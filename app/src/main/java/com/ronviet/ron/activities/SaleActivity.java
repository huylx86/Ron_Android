package com.ronviet.ron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.AreaRecyclerViewAdapter;
import com.ronviet.ron.adapters.TableRecyclerViewAdapter;
import com.ronviet.ron.api.APIConstants;
import com.ronviet.ron.api.ResponseAreaInfoData;
import com.ronviet.ron.api.ResponseCreateMaPhieuData;
import com.ronviet.ron.api.ResponseCreatePhieuData;
import com.ronviet.ron.api.ResponseTableInfoData;
import com.ronviet.ron.api.SaleAPIHelper;
import com.ronviet.ron.models.AreaInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;

import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends BaseActivity {

    private RecyclerView mRecyclerTables, mRecyclerAreas;
    private TableRecyclerViewAdapter mAdapterTable;
    private AreaRecyclerViewAdapter mAdapterArea;
    private View mSubMenu;
    private List<TableInfo> mLstTables;
    private List<AreaInfo> mLstAreas;
    private TableInfo mCurrentTableSelection;
    private SaleAPIHelper mSaleHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        mContext = this;
        mSaleHelper = new SaleAPIHelper();
        initLayout();
    }

    private void initLayout()
    {
        LinearLayout lnOrder = (LinearLayout)findViewById(R.id.ln_order);
        lnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SaleActivity.this, OrderReviewActivity.class));
            }
        });
        mRecyclerTables = (RecyclerView) findViewById(R.id.recycler_view_tables);
        mRecyclerAreas = (RecyclerView) findViewById(R.id.recycler_view_area);
        mSubMenu = findViewById(R.id.ln_sub_menu);

        dummyTableData();
        dummyAreaTableData();

        GridLayoutManager tableLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerTables.setLayoutManager(tableLayoutManager);
        mAdapterTable = new TableRecyclerViewAdapter(this, mLstTables, mHandlerProcessTable);
        mRecyclerTables.setAdapter(mAdapterTable);

        LinearLayoutManager areaLayoutManager = new LinearLayoutManager(this);
        areaLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerAreas.setLayoutManager(areaLayoutManager);
        mAdapterArea = new AreaRecyclerViewAdapter(this, mLstAreas, mHandlerAreaSelection);
        mRecyclerAreas.setAdapter(mAdapterArea);

        initHeader();
        setTitle(getString(R.string.title_table_map));
        setTotal("0");
    }

    private void loadAreaData()
    {
        mLstAreas = new ArrayList<>();
        mSaleHelper.getAreaInfo(mContext, mHandlerGetArea, true);
    }

    private void loadTableData(long areaId)
    {
        mLstTables = new ArrayList<>();
        mSaleHelper.getTableInfo(mContext, areaId, mHanlderGetTable, true);
    }

    private void createMaPhieu()
    {
        mSaleHelper.getMaPhieu(mContext, mHandlerGetMaPhieu, true);
    }

    private void dummyTableData()
    {
        mLstTables = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            TableInfo info = new TableInfo();
            info.setName(String.valueOf(i));
//            info.setOrder(true);
            if(i%2 == 0) {
//                info.setOrder(false);
            }
//            info.setSelection(false);
            mLstTables.add(info);
        }
    }

    private void dummyAreaTableData()
    {
        mLstAreas = new ArrayList<>();
        for(int i = 0 ;i<5; i++){
            AreaInfo area = new AreaInfo();
            area.setName(String.valueOf("Khu " + i));
            area.setmIsSelection(false);
            if(i==0) {
                area.setmIsSelection(true);
            }
            mLstAreas.add(area);
        }
    }

    private Handler mHandlerGetArea = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseAreaInfoData res = (ResponseAreaInfoData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mLstAreas = res.data;
                        if(mLstAreas.size() > 0) {
                            mLstAreas.get(0).setmIsSelection(true);
                        }
                        mAdapterArea.updateData(mLstAreas);
                        loadTableData(mLstAreas.get(0).getId());
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

    private Handler mHanlderGetTable = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseTableInfoData res = (ResponseTableInfoData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mLstTables = res.data;
                        mAdapterTable.updateData(mLstTables);
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

    private Handler mHandlerGetMaPhieu = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCreateMaPhieuData res = (ResponseCreateMaPhieuData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        if(mCurrentTableSelection != null) {
                            mCurrentTableSelection.setMaPhieu(res.data.maPhieu);
                            mSaleHelper.getIdPhieu(mContext, mCurrentTableSelection.getMaPhieu(), mCurrentTableSelection.getAreaId(),
                                    mCurrentTableSelection.getId(), mHandlerGetIdPhieu, true);
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

    private Handler mHandlerGetIdPhieu = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCreatePhieuData res = (ResponseCreatePhieuData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        if(mCurrentTableSelection != null) {
                            mCurrentTableSelection.setIdPhieu(res.data.idPhieu);
                            Intent iProduct = new Intent(mContext, ProductActivity.class);
                            iProduct.putExtra(Constants.EXTRA_TABLE, mCurrentTableSelection);
                            mContext.startActivity(iProduct);
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


    protected Handler mHandlerProcessTable = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.HANDLER_CLOSE_SUB_MENU:
                    mTableSelection = (TableInfo)msg.obj;
                    mSubMenu.setVisibility(View.GONE);
                    break;
                case Constants.HANDLER_OPEN_SUB_MENU:
                    mTableSelection = (TableInfo)msg.obj;
                    mSubMenu.setVisibility(View.VISIBLE);
                    break;
                case Constants.HANDLER_OPEN_TABLE:
                    mCurrentTableSelection = (TableInfo) msg.obj;
                    if(mCurrentTableSelection.getMaPhieu() == null) {
                        new SaleAPIHelper().getMaPhieu(mContext, mHandlerGetMaPhieu, true);
                    } else {
                        Intent iProduct = new Intent(mContext, ProductActivity.class);
                        iProduct.putExtra(Constants.EXTRA_TABLE, mCurrentTableSelection);
                        mContext.startActivity(iProduct);
                    }
                    break;
            }
        }
    };

    protected Handler mHandlerAreaSelection = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos = msg.what;
            loadTableData(mLstAreas.get(pos).getId());
        }
    };
}
