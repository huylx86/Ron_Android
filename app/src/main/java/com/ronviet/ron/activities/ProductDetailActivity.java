package com.ronviet.ron.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.ProductRecyclerViewAdapter;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseCommon;
import com.ronviet.ron.api.ResponseCreateOrderCodeData;
import com.ronviet.ron.api.ResponseProductData;
import com.ronviet.ron.api.SaleAPIHelper;
import com.ronviet.ron.models.OrderInfo;
import com.ronviet.ron.models.PendingOrder;
import com.ronviet.ron.models.ProductCatInfo;
import com.ronviet.ron.models.ProductInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {

    private ProductRecyclerViewAdapter mAdapterProduct;
    private List<ProductInfo> mLstProducts;
    private ProductCatInfo mProductCatInfo;
    private SaleAPIHelper mSaleApiHelper;
    private OrderInfo mCurrentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mContext = this;
        mProductCatInfo = (ProductCatInfo)getIntent().getSerializableExtra(Constants.EXTRA_PRODUCT);
        mTableSelection = (TableInfo)getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
        mSaleApiHelper = new SaleAPIHelper(mContext);
        mLstProducts = new ArrayList<>();
//        dummyData();
        initLayout();
        loadData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mTableSelection != null) {
            PendingOrder pendingOrder = SharedPreferenceUtils.getPendingOrderFromList(mContext, mTableSelection.getId());
            if (pendingOrder != null) {
                setTotal(pendingOrder.tongTien);
            } else {
                setTotal(0);
            }
        }
    }

    private void initLayout()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_product);
        GridLayoutManager tableLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(tableLayoutManager);
        mAdapterProduct = new ProductRecyclerViewAdapter(this, mLstProducts, mTableSelection, mHandlerInputSoLuong);
        recyclerView.setAdapter(mAdapterProduct);

        initHeader();

        PendingOrder pendingOrder = SharedPreferenceUtils.getPendingOrderFromList(mContext, mTableSelection.getId());
        if(pendingOrder != null) {
            setTotal(pendingOrder.tongTien);
        } else {
            setTotal(0);
        }
        if(mProductCatInfo != null) {
            String tenNhom = mProductCatInfo.getTenNhom();
            if(tenNhom.length()>5) {
                tenNhom = tenNhom.substring(0, 5) + "...";
            }
            setTitle(tenNhom + " - " + mTableSelection.getName());
        }
    }

    private void dummyData()
    {
        mLstProducts = new ArrayList<>();
        for(int i=0; i<20; i++) {
            ProductInfo info = new ProductInfo();
            info.setId(i);
            info.setTenMon(mProductCatInfo.getTenNhom() + " - " + i);
            info.setDonGia(i);
            mLstProducts.add(info);
        }
    }

    private void loadData()
    {
        mSaleApiHelper.getProducts(mTableSelection.getAreaId(), mProductCatInfo.getId(), mHandlerGetProduct, true );
    }


    private Handler mHandlerGetProduct = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseProductData res = (ResponseProductData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mLstProducts = res.data;
                        mAdapterProduct.updateData(mLstProducts);
                    } else {
                        if(res.message != null) {
                            new DialogUtiils().showDialog(mContext, res.message, false);
                        } else {
                            new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                        }
                    }
                    break;
                case APIConstants.HANDLER_REQUEST_SERVER_FAILED:
                    new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                    break;
            }
        }
    };

    protected Handler mHandlerInputSoLuong = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCurrentOrder = (OrderInfo) msg.obj;
            PendingOrder pendingOrder = SharedPreferenceUtils.getPendingOrderFromList(mContext, mTableSelection.getId());

            String orderCode = null;
            if(pendingOrder != null){
                orderCode = pendingOrder.orderCode;
            }

            if(orderCode != null) {
                float tongTien = pendingOrder.tongTien + mCurrentOrder.getDonGia()*mCurrentOrder.getSoLuong();
                SharedPreferenceUtils.updateTongTienToPendingOrder(mContext, mTableSelection.getId(), tongTien);
                setTotal(tongTien);
                mSaleApiHelper.submitOrderTungMon(orderCode, mTableSelection.getIdPhieu(),mCurrentOrder.getId(),
                        mCurrentOrder.getMaMon(), mCurrentOrder.getTenMon(), mCurrentOrder.getSoLuong(), mCurrentOrder.getDonViTinhId(),
                        mCurrentOrder.getGiaGoc(), mCurrentOrder.getDonGia(), mCurrentOrder.isGiaCoThue(), mCurrentOrder.getThue(), mTableSelection.getId(), "",
                        "INSERT", mHandlerSubmitOrderTungMon, true);
            } else {
                mSaleApiHelper.getOrderCode(mHandlerGetOrderCode, true);
            }

        }
    };

    private Handler mHandlerGetOrderCode = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCreateOrderCodeData res = (ResponseCreateOrderCodeData) msg.obj;

                    //Store order code for using next time till submit this order
                    if(res.data != null && res.data.orderCode != null) {
                        PendingOrder order = new PendingOrder();
                        order.banId = mTableSelection.getId();
                        order.orderCode = res.data.orderCode;
                        order.tongTien = mCurrentOrder.getDonGia()*mCurrentOrder.getSoLuong();
                        SharedPreferenceUtils.savePendingOrder(mContext, order);

                        setTotal(order.tongTien);

                        if (res.code == APIConstants.REQUEST_OK) {
                            mSaleApiHelper.submitOrderTungMon(res.data.orderCode, mTableSelection.getIdPhieu(), mCurrentOrder.getId(),
                                    mCurrentOrder.getMaMon(), mCurrentOrder.getTenMon(), mCurrentOrder.getSoLuong(), mCurrentOrder.getDonViTinhId(),
                                    mCurrentOrder.getGiaGoc(), mCurrentOrder.getDonGia(), mCurrentOrder.isGiaCoThue(), mCurrentOrder.getThue(), mTableSelection.getId(), "",
                                    "INSERT", mHandlerSubmitOrderTungMon, true);
                        } else {
                            if (res.message != null) {
                                new DialogUtiils().showDialog(mContext, res.message, false);
                            } else {
                                new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                            }
                        }
                    } else {
                        if (res.message != null) {
                            new DialogUtiils().showDialog(mContext, res.message, false);
                        } else {
                            new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                        }
                    }
                    break;
                case APIConstants.HANDLER_REQUEST_SERVER_FAILED:
                    new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                    break;
            }
        }
    };

    private Handler mHandlerSubmitOrderTungMon = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {

                    } else {
                        if(res.message != null) {
                            new DialogUtiils().showDialog(mContext, res.message, false);
                        } else {
                            new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                        }
                    }
                    break;
                case APIConstants.HANDLER_REQUEST_SERVER_FAILED:
                    new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                    break;
            }
        }
    };
}
