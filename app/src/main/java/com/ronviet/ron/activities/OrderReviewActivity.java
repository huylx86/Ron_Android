package com.ronviet.ron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.DividerItemDecoration;
import com.ronviet.ron.adapters.OrderReviewRecyclerViewAdapter;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseCommon;
import com.ronviet.ron.api.ResponseReviewOrderData;
import com.ronviet.ron.api.SaleAPIHelper;
import com.ronviet.ron.models.OrderInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.CommonUtils;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderReviewActivity extends BaseActivity {

    private OrderReviewRecyclerViewAdapter mAdapterOrder;
    private List<OrderInfo> mLstOrders;
    private Button mBtnSubmitOrder;
    private SaleAPIHelper mSaleApiHelper;
    private TextView mTvTongTien;

    private String mCurrentOrderCode;
    private boolean mIsChangeData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = this;
        mTableSelection = (TableInfo) getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
        mSaleApiHelper = new SaleAPIHelper(mContext);
        mLstOrders = new ArrayList<>();
//        dummyData();
        initLayout();
        loadData();
    }

    private void initLayout()
    {
        mBtnSubmitOrder = (Button)findViewById(R.id.btn_submit_order);
        mTvTongTien = (TextView)findViewById(R.id.tv_tong_tien);
        RecyclerView rvOrder = (RecyclerView)findViewById(R.id.recycler_view_order);
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(orderLayoutManager);
        rvOrder.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvOrder.setItemAnimator(new DefaultItemAnimator());
        mAdapterOrder = new OrderReviewRecyclerViewAdapter(this, mLstOrders, mHandlerInputSoLuong);
        rvOrder.setAdapter(mAdapterOrder);

        mBtnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaleApiHelper.confirmOrder(mTableSelection.getIdPhieu(), mTableSelection.getId(), mCurrentOrderCode, mHandlerConfirmOrder, true);
            }
        });

        initHeader();
        setTitle(getString(R.string.title_list_order) + " - " + mTableSelection.getName());
        setAddOrder();
    }

    private void loadData()
    {
        mLstOrders = new ArrayList<>();
        mCurrentOrderCode = SharedPreferenceUtils.getOrderCodeFromPendingOrder(mContext, mTableSelection.getId());
        if(mCurrentOrderCode != null) {
            mSaleApiHelper.getReviewOrder(mCurrentOrderCode, mHandlerReviewOrder, true);
        } else {
            processAddOrder();
        }
    }

    @Override
    protected void processAddOrder()
    {
        if(mIsChangeData) {
            new DialogUtiils().showDialogConfirm(mContext, getString(R.string.message_confirm_move_to_add_order), mHandlerProcessMoveToAddOrder);
        } else {
            super.processAddOrder();
        }
    }

    private Handler mHandlerReviewOrder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseReviewOrderData res = (ResponseReviewOrderData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mLstOrders = res.data;
                        int tongTien = 0;
                        for(OrderInfo order : mLstOrders){
                            float total = order.getSoLuong()*order.getDonGia();
                            order.setTotal(total);
                            tongTien +=total;
                        }
                        mTvTongTien.setText(CommonUtils.formatCurrency(tongTien));
                        mAdapterOrder.updateData(mLstOrders);
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

    private Handler mHandlerConfirmOrder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        //Remove order code of table after submit order succesfully
                        SharedPreferenceUtils.removeOrderCode(mContext, mCurrentOrderCode);
                        Intent iHome = new Intent(OrderReviewActivity.this, HomeActivity.class);
                        iHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        iHome.putExtra(Constants.EXTRA_RESTART_SALE_SCREEN, true);
                        startActivity(iHome);
                        finish();
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
            OrderInfo orderInfo = (OrderInfo) msg.obj;
            switch (msg.what){
                case Constants.HANDLER_INPUT_SO_LUONG:
                    mSaleApiHelper.submitOrderTungMon(orderInfo.getOrderCode(), mTableSelection.getIdPhieu(), orderInfo.getId(),
                            orderInfo.getMaMon(), orderInfo.getTenMon(), orderInfo.getSoLuong(), orderInfo.getDonViTinhId(),
                            orderInfo.getGiaGoc(), orderInfo.getDonGia(), orderInfo.isGiaCoThue(), orderInfo.getThue(), mTableSelection.getId(), "",
                            "UPDATE", mHandlerProcessOrderTungMon, true);

                    break;
                case Constants.HANDLER_XOA_ORDER:
                    mSaleApiHelper.deleteOrderTungMon(mTableSelection.getIdPhieu(), orderInfo.getOrderId(), orderInfo.getOrderChiTietPhieu(),
                            mHandlerProcessOrderTungMon, true);
                    break;
            }


        }
    };

    private Handler mHandlerProcessOrderTungMon = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mIsChangeData = true;
                        loadData();
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

    protected Handler mHandlerProcessMoveToAddOrder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            OrderReviewActivity.super.processAddOrder();
        }
    };

    private void dummyData() {
        mLstOrders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderInfo info = new OrderInfo();
            info.setTenMon("Product " + i);
            info.setDonGia(i * 1000 + 1000);
            info.setPromotion("Giam gia " + i * 1000);
            info.setSoLuong(i);
            info.setTotal((i * 1000 + 1000) * i);
//            info.setReturnProd(false);
            mLstOrders.add(info);
        }
    }
}
