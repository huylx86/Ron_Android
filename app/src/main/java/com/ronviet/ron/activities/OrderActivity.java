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
import com.ronviet.ron.adapters.OrderRecyclerViewAdapter;
import com.ronviet.ron.models.OrderInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

    private OrderRecyclerViewAdapter mAdapterOrder;
    private List<OrderInfo> mLstOrders;
    private Button mBtnSubmitOrder;
    private TableInfo mTableInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = this;
        mTableSelection = (TableInfo) getIntent().getSerializableExtra(Constants.EXTRA_TABLE);
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
        mAdapterOrder = new OrderRecyclerViewAdapter(this, mLstOrders, mHandlerProcessSubmitOrder);
        rvOrder.setAdapter(mAdapterOrder);

        initHeader();
        setTitle(getString(R.string.title_list_order));
        setAddOrder();
    }

    private void dummyData()
    {
        mLstOrders = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            OrderInfo info = new OrderInfo();
            info.setTenMon("Product " + i);
            info.setDonGia(i*1000 + 1000);
            info.setPromotion("Giam gia " + i*1000);
            info.setNumber(i);
            info.setTotal((i*1000 + 1000)*i);
//            info.setReturnProd(false);
            mLstOrders.add(info);
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
                    mBtnSubmitOrder.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
}
