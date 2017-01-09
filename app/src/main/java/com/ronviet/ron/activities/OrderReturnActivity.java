package com.ronviet.ron.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.DividerItemDecoration;
import com.ronviet.ron.adapters.OrderReturnRecyclerViewAdapter;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseCommon;
import com.ronviet.ron.api.ResponseReturnOrderData;
import com.ronviet.ron.api.SaleAPIHelper;
import com.ronviet.ron.models.OrderReturnInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;

import java.util.ArrayList;
import java.util.List;

public class OrderReturnActivity extends BaseActivity {

    private OrderReturnRecyclerViewAdapter mAdapterReturnOrder;
    private List<OrderReturnInfo> mLstReturnOrders;
    private Button mBtnSubmitOrder;
    private SaleAPIHelper mSaleApiHelper;

    //TODO: How to Get Order Code?
    private String mCurrentOrderCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = this;
        mTableSelection = (TableInfo) getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
        mSaleApiHelper = new SaleAPIHelper();
        mLstReturnOrders = new ArrayList<>();
        dummyData();
        initLayout();
    }

    private void initLayout()
    {
        mBtnSubmitOrder = (Button)findViewById(R.id.btn_submit_order);
        RecyclerView rvOrder = (RecyclerView)findViewById(R.id.recycler_view_order);
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(orderLayoutManager);
        rvOrder.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvOrder.setItemAnimator(new DefaultItemAnimator());
        mAdapterReturnOrder = new OrderReturnRecyclerViewAdapter(this, mLstReturnOrders, mHandlerProcessSubmitOrder);
        rvOrder.setAdapter(mAdapterReturnOrder);

        mBtnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaleApiHelper.confirmReturn(mContext, mTableSelection.getId(), mCurrentOrderCode, mHandlerConfirmReturnOrder, true);
            }
        });

        initHeader();
        setTitle(getString(R.string.title_list_order));
        setAddOrder();
    }

    private void loadData()
    {
        mLstReturnOrders = new ArrayList<>();
        mSaleApiHelper.getOrderForReturn(mContext, mTableSelection.getIdPhieu(), mHandlerReturnOrder, true );
    }

    private Handler mHandlerReturnOrder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseReturnOrderData res = (ResponseReturnOrderData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mLstReturnOrders = res.data;
                        mAdapterReturnOrder.updateData(mLstReturnOrders);
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

    private Handler mHandlerConfirmReturnOrder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
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

    private void dummyData()
    {
        mLstReturnOrders = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            OrderReturnInfo info = new OrderReturnInfo();
            info.setTenMon("Product " + i);
            info.setDonGia(i*1000 + 1000);
            info.setPromotion("Giam gia " + i*1000);
            info.setSoLuong(i);
            info.setTotal((i*1000 + 1000)*i);
//            info.setReturnProd(false);
            mLstReturnOrders.add(info);
        }
    }

    protected Handler mHandlerProcessSubmitOrder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.HANDLER_CLOSE_SUB_MENU:
                    mBtnSubmitOrder.setVisibility(View.GONE);
                    break;
                case Constants.HANDLER_OPEN_SUB_MENU:
                    OrderReturnInfo order = (OrderReturnInfo) msg.obj;
                    mSaleApiHelper.submitReturnOrderTungMon(mContext, order.getIdChiTietPhieu(), order.getIdPhieu(), order.getId(),
                                                            order.getMaMon(), order.getTenMon(), order.getSoLuong(), order.getDonViTinhId(), "",
                                                            mHandlerReturnOrderTungMon, true);
                    mBtnSubmitOrder.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private Handler mHandlerReturnOrderTungMon = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
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
