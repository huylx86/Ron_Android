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
import com.ronviet.ron.adapters.OrderReviewRecyclerViewAdapter;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseCommon;
import com.ronviet.ron.api.ResponseReviewOrderData;
import com.ronviet.ron.api.SaleAPIHelper;
import com.ronviet.ron.models.OrderInfo;
import com.ronviet.ron.models.TableInfo;
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

    //TODO: How to Get Order Code?
    private String mCurrentOrderCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = this;
        mTableSelection = (TableInfo) getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
        mSaleApiHelper = new SaleAPIHelper();
        mLstOrders = new ArrayList<>();
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
        mAdapterOrder = new OrderReviewRecyclerViewAdapter(this, mLstOrders);
        rvOrder.setAdapter(mAdapterOrder);

        mBtnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : Open comment to submit confirm order
                new DialogUtiils().showDialog(mContext, "Gửi Order thành công!", true);
//                mSaleApiHelper.confirmOrder(mContext, mTableSelection.getIdPhieu(), mTableSelection.getId(), mCurrentOrderCode, mHandlerConfirmOrder, true);
            }
        });

        initHeader();
        setTitle(getString(R.string.title_list_order));
        setAddOrder();
    }

    private void loadData()
    {
        mLstOrders = new ArrayList<>();
        mCurrentOrderCode = SharedPreferenceUtils.getOrderCodeFromPendingOrder(mContext, mTableSelection.getId());
        if(mCurrentOrderCode != null) {
            mSaleApiHelper.getReviewOrder(mContext, mCurrentOrderCode, mHandlerReviewOrder, true);
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
                        if(res.message != null) {
                            new DialogUtiils().showDialog(mContext, res.message, true);
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
