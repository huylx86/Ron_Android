package com.ronviet.ron.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.ProductRecyclerViewAdapter;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseCreateOrderCodeData;
import com.ronviet.ron.api.SaleAPIHelper;
import com.ronviet.ron.models.OrderInfo;
import com.ronviet.ron.models.ProductCatInfo;
import com.ronviet.ron.models.ProductInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {

    private ProductRecyclerViewAdapter mAdapterProduct;
    private List<ProductInfo> mLstProducts;
    private ProductCatInfo mProductCatInfo;
    private TableInfo mTableInfo;
    private SaleAPIHelper mSaleApiHelper;
    private OrderInfo mCurrentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mContext = this;
        mProductCatInfo = (ProductCatInfo)getIntent().getSerializableExtra(Constants.EXTRA_PRODUCT);
        mTableInfo = (TableInfo)getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
        mTableSelection = mTableInfo;
        mSaleApiHelper = new SaleAPIHelper();
        dummyData();
        initLayout();

    }

    private void initLayout()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_product);
        GridLayoutManager tableLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(tableLayoutManager);
        mAdapterProduct = new ProductRecyclerViewAdapter(this, mLstProducts, mTableInfo, mHandlerInputSoLuong);
        recyclerView.setAdapter(mAdapterProduct);

        initHeader();
        setTotal(String.valueOf(mTableInfo.getTotal()));
        if(mProductCatInfo != null) {
            setTitle(mProductCatInfo.getTenNhom());
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

    protected Handler mHandlerInputSoLuong = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCurrentOrder = (OrderInfo) msg.obj;
            mSaleApiHelper.getOrderCode(mContext, mHandlerGetOrderCode, true);
        }
    };

    private Handler mHandlerGetOrderCode = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCreateOrderCodeData res = (ResponseCreateOrderCodeData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mSaleApiHelper.submitOrderTungMon(mContext, res.data.orderCode, mTableInfo.getIdPhieu(),mCurrentOrder.getId(),
                                mCurrentOrder.getMaMon(), mCurrentOrder.getTenMon(), mCurrentOrder.getSoLuong(), mCurrentOrder.getDonViTinhId(),
                                mCurrentOrder.getGiaGoc(), mCurrentOrder.getDonGia(), mCurrentOrder.isGiaCoThue(), mCurrentOrder.getThue(), mTableInfo.getId(), "",
                                mHandlerSubmitOrderTungMon, true);
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

    private Handler mHandlerSubmitOrderTungMon = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCreateOrderCodeData res = (ResponseCreateOrderCodeData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        if(res.message != null) {
                            new DialogUtiils().showDialog(mContext, res.message, false);
                        }
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
