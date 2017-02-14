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
import com.ronviet.ron.adapters.OrderReturnRecyclerViewAdapter;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseCommon;
import com.ronviet.ron.api.ResponseCreateOrderCodeData;
import com.ronviet.ron.api.ResponseReturnOrderData;
import com.ronviet.ron.api.SaleAPIHelper;
import com.ronviet.ron.models.OrderReturnInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.CommonUtils;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;

import java.util.ArrayList;
import java.util.List;

public class OrderReturnActivity extends BaseActivity {

    private OrderReturnRecyclerViewAdapter mAdapterReturnOrder;
    private List<OrderReturnInfo> mLstReturnOrders;
    private Button mBtnSubmitOrder;
    private SaleAPIHelper mSaleApiHelper;

    private String mCurrentOrderCode;
    private OrderReturnInfo mCurrentSelectedOrderInfo;

    private TextView mTvTongTien;
    private boolean mIsChangeData = false;
    private boolean mIsBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = this;
        mTableSelection = (TableInfo) getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
        mSaleApiHelper = new SaleAPIHelper(mContext);
        mLstReturnOrders = new ArrayList<>();

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
        mAdapterReturnOrder = new OrderReturnRecyclerViewAdapter(this, mLstReturnOrders, mHandlerProcessSubmitOrder);
        rvOrder.setAdapter(mAdapterReturnOrder);

        mBtnSubmitOrder.setVisibility(View.GONE);
        mBtnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaleApiHelper.confirmReturn(mTableSelection.getIdPhieu(), mCurrentOrderCode, mHandlerConfirmReturnOrder, true);
            }
        });
        mBtnSubmitOrder.setText(R.string.confirm);

        initHeader();
        setTitle(getString(R.string.title_return_order) + " - " + mTableSelection.getName());
        setAddOrder();
    }

    private void loadData()
    {
        mLstReturnOrders = new ArrayList<>();
        mSaleApiHelper.khoiTaoTraHang(mTableSelection.getIdPhieu(), mHandlerKhoiTaoTraHang, true);

    }

    @Override
    public void onBackPressed() {
        mIsBackPressed = true;
        if(mIsChangeData) {
            new DialogUtiils().showDialogConfirm(mContext, getString(R.string.massage_cancel_return_order), mHandlerProcessCancelOrder);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void processAddOrder()
    {
        mIsBackPressed = false;
        if(mIsChangeData) {
            new DialogUtiils().showDialogConfirm(mContext, getString(R.string.massage_cancel_return_order), mHandlerProcessCancelOrder);
        } else {
            super.processAddOrder();
        }
    }

    private Handler mHandlerKhoiTaoTraHang = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mSaleApiHelper.getOrderForReturn(mTableSelection.getIdPhieu(), mHandlerReturnOrder, true );
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

    private Handler mHandlerReturnOrder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseReturnOrderData res = (ResponseReturnOrderData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mLstReturnOrders = res.data;
                        int tongTien = 0;
                        for(OrderReturnInfo order : mLstReturnOrders){
                            float total = order.getSoLuong()*order.getDonGia();
                            order.setTotal(total);
                            tongTien +=total;
                        }
                        mTvTongTien.setText(CommonUtils.formatCurrency(tongTien));
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
                        Intent iHome = new Intent(OrderReturnActivity.this, HomeActivity.class);
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

    protected Handler mHandlerProcessSubmitOrder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.HANDLER_CLOSE_SUB_MENU:
                    mBtnSubmitOrder.setVisibility(View.GONE);
                    mIsChangeData = false;
                    break;
                case Constants.HANDLER_OPEN_SUB_MENU:
                    mCurrentSelectedOrderInfo = (OrderReturnInfo) msg.obj;

                    if(mCurrentOrderCode != null) {
                        mSaleApiHelper.submitReturnOrderTungMon(mCurrentSelectedOrderInfo.getIdChiTietPhieu(), mCurrentSelectedOrderInfo.getIdPhieu(), mCurrentSelectedOrderInfo.getId(),
                                mCurrentSelectedOrderInfo.getMaMon(), mCurrentSelectedOrderInfo.getTenMon(), mCurrentSelectedOrderInfo.getSoLuongTra(), mCurrentSelectedOrderInfo.getDonViTinhId(), "",
                                mHandlerReturnOrderTungMon, true);
                    } else {
                        mSaleApiHelper.getOrderCode(mHandlerGetOrderCode, true);
                    }
                    mBtnSubmitOrder.setVisibility(View.VISIBLE);
                    mIsChangeData = true;
                    break;
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
                        mCurrentOrderCode = res.data.orderCode;
                        if (res.code == APIConstants.REQUEST_OK) {
                            mSaleApiHelper.submitReturnOrderTungMon(mCurrentSelectedOrderInfo.getIdChiTietPhieu(), mCurrentSelectedOrderInfo.getIdPhieu(), mCurrentSelectedOrderInfo.getId(),
                                    mCurrentSelectedOrderInfo.getMaMon(), mCurrentSelectedOrderInfo.getTenMon(), mCurrentSelectedOrderInfo.getSoLuongTra(), mCurrentSelectedOrderInfo.getDonViTinhId(), "",
                                                            mHandlerReturnOrderTungMon, true);
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
    private Handler mHandlerReturnOrderTungMon = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        if(mCurrentSelectedOrderInfo.getSoLuong() < 1) {
                            mLstReturnOrders.remove(mCurrentSelectedOrderInfo);
                        }
                        int tongTien = 0;
                        for(OrderReturnInfo order : mLstReturnOrders){
                            float total = order.getSoLuong()*order.getDonGia();
                            order.setTotal(total);
                            tongTien +=total;
                        }
                        mTvTongTien.setText(CommonUtils.formatCurrency(tongTien));
                        mAdapterReturnOrder.updateData(mLstReturnOrders);
//                        mLstReturnOrders = new ArrayList<>();
//                        mSaleApiHelper.getOrderForReturn(mContext, mTableSelection.getIdPhieu(), mHandlerReturnOrder, true );
                    } else {
                        mCurrentSelectedOrderInfo.setSoLuong(mCurrentSelectedOrderInfo.getSoLuong() + mCurrentSelectedOrderInfo.getSoLuongTra());
                        mAdapterReturnOrder.notifyDataSetChanged();
                        if(res.message != null) {
                            new DialogUtiils().showDialog(mContext, res.message, false);
                        } else {
                            new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                        }
                    }
                    break;
                case APIConstants.HANDLER_REQUEST_SERVER_FAILED:
                    mCurrentSelectedOrderInfo.setSoLuong(mCurrentSelectedOrderInfo.getSoLuong() + mCurrentSelectedOrderInfo.getSoLuongTra());
                    mAdapterReturnOrder.notifyDataSetChanged();
                    new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                    break;
            }
        }
    };

    protected Handler mHandlerProcessCancelOrder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSaleApiHelper.cancelReturnOrder(mTableSelection.getIdPhieu(), mHandlerCancelReturnOrder, true);
        }
    };

    private Handler mHandlerCancelReturnOrder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        if(mIsBackPressed) {
                            finish();
                        } else {
                            OrderReturnActivity.super.processAddOrder();
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
                    mCurrentSelectedOrderInfo.setSoLuong(mCurrentSelectedOrderInfo.getSoLuong() + mCurrentSelectedOrderInfo.getSoLuongTra());
                    mAdapterReturnOrder.notifyDataSetChanged();
                    new DialogUtiils().showDialog(mContext, getString(R.string.server_error), false);
                    break;
            }
        }
    };

//    private void showDialogConfirm()
//    {
//        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
//        dialog.setContentView(R.layout.dialog_confirm_return_order_layout);
//        dialog.setTitle(getString(R.string.title_message));
//
//        TextView tvOk = (TextView)dialog.findViewById(R.id.tv_ok);
//        TextView tvCancel = (TextView)dialog.findViewById(R.id.tv_cancel);
//        TextView tvMessage = (TextView)dialog.findViewById(R.id.tv_text_message);
//        tvMessage.setText(Html.fromHtml(getString(R.string.massage_cancel_return_order)));
//
//        tvOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mSaleApiHelper.cancelReturnOrder(mContext, mTableSelection.getIdPhieu(), mHandlerCancelReturnOrder, true);
//                dialog.dismiss();
//            }
//        });
//
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//
//        // show it
//        dialog.show();
//
//    }
}
