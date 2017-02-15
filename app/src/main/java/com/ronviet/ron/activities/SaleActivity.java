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
import com.ronviet.ron.api.ResponseCommon;
import com.ronviet.ron.api.ResponseCreateMaPhieuData;
import com.ronviet.ron.api.ResponseCreatePhieuData;
import com.ronviet.ron.api.ResponseTableInfoData;
import com.ronviet.ron.api.SaleAPIHelper;
import com.ronviet.ron.models.AreaInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SaleActivity extends BaseActivity {

    private RecyclerView mRecyclerTables, mRecyclerAreas;
    private TableRecyclerViewAdapter mAdapterTable;
    private AreaRecyclerViewAdapter mAdapterArea;
    private View mSubMenu;
    private List<TableInfo> mLstTables;
    private List<AreaInfo> mLstAreas;
    private SaleAPIHelper mSaleHelper;
    private static int mSelectedArea = 0;
    private static int mSelectedTable = -1, mSelectedTNewTable = -1;
    private boolean isMoveTable = false;
    private long idBanCu = -1;
    private long idBanMoi = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        mContext = this;
        mSaleHelper = new SaleAPIHelper(mContext);
        mLstAreas = new ArrayList<>();
        mLstTables = new ArrayList<>();

        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAreaData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timerRefreshTable != null){
            timerRefreshTable.cancel();
        }
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
        View vReturnOrder = findViewById(R.id.fl_return_order);
        vReturnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iReturnOrder = new Intent(mContext, OrderReturnActivity.class);
                iReturnOrder.putExtra(Constants.EXTRA_TABLE, mTableSelection);
                startActivity(iReturnOrder);
            }
        });

        View vMoveTable = findViewById(R.id.fl_move_table);
        vMoveTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogUtiils().showDialogConfirm(mContext, getString(R.string.message_confirm), new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        isMoveTable = true;
                        mAdapterTable.setIsMoveTable(isMoveTable);
                    }
                });
            }
        });

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
        setTotal(0);
    }
    Timer timerRefreshTable;
    private void initTimerToRefreshTable() {
        timerRefreshTable = new Timer();
        final Handler handler = new Handler();
        TimerTask doRefreshTableTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
//                        Toast.makeText(mContext, "Demo Timer", Toast.LENGTH_LONG).show();
                        loadTableData(mLstAreas.get(mSelectedArea).getId());
                    }
                });
            }
        };
        timerRefreshTable.schedule(doRefreshTableTask, 1000, 10000);
    }
    private void loadAreaData()
    {
        mLstAreas = new ArrayList<>();
        mLstTables = new ArrayList<>();
        mSaleHelper.getAreaInfo(mHandlerGetArea, true);
    }

    private void loadTableData(long areaId)
    {
        mLstTables = new ArrayList<>();
        mSaleHelper.getTableInfo(areaId, mHanlderGetTable, true);
    }


    private Handler mHandlerGetArea = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseAreaInfoData res = (ResponseAreaInfoData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        mLstAreas = res.data;
                        mLstTables = new ArrayList<>();
                        if(mLstAreas.size() > 0 && mSelectedArea < mLstAreas.size()) {
                            mLstAreas.get(mSelectedArea).setmIsSelection(true);
                            loadTableData(mLstAreas.get(mSelectedArea).getId());
                            initTimerToRefreshTable();
                        }
                        mAdapterArea.updateData(mLstAreas);
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
                        mLstTables.addAll(res.data);
                        mAdapterTable.updateData(mLstTables);
                        if(mSelectedTable > -1 && mSelectedTable < mLstTables.size()) {
                            mTableSelection = mLstTables.get(mSelectedTable);
                            mTableSelection.setSelection(true);
                            setTotal(mTableSelection.getTotal());
                            if(mTableSelection.getIdPhieu() > 0) {
                                mSubMenu.setVisibility(View.VISIBLE);
                            } else {
                                mSubMenu.setVisibility(View.GONE);
                            }
                            mRecyclerTables.scrollToPosition(mSelectedTable);
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

    private Handler mHandlerGetMaPhieu = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCreateMaPhieuData res = (ResponseCreateMaPhieuData) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        if(mTableSelection != null) {
                            mTableSelection.setMaPhieu(res.data.maPhieu);
                            mSaleHelper.getIdPhieu(mTableSelection.getAreaId(),
                                    mTableSelection.getId(), mHandlerGetIdPhieu, true);
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
                        if(mTableSelection != null) {
                            if(res.data != null) {
                                mTableSelection.setIdPhieu(res.data.idPhieu);
                                Intent iProduct = new Intent(mContext, ProductActivity.class);
                                iProduct.putExtra(Constants.EXTRA_TABLE, mTableSelection);
                                mContext.startActivity(iProduct);
                            }
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

    private Handler mHandlerMoveTable = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIConstants.HANDLER_REQUEST_SERVER_SUCCESS:
                    ResponseCommon res = (ResponseCommon) msg.obj;
                    if(res.code == APIConstants.REQUEST_OK) {
                        new DialogUtiils().showDialog(mContext, res.message, false);
                        SharedPreferenceUtils.updateTableIdToNewOne(mContext, idBanCu, idBanMoi);
                        mSelectedTable = mSelectedTNewTable;
                        loadTableData(mLstAreas.get(mSelectedArea).getId());
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
                    if(isMoveTable) {
                        TableInfo newTableInfo = (TableInfo) msg.obj;
                        idBanCu = mTableSelection.getId();
                        idBanMoi = newTableInfo.getId();
                        mSaleHelper.chuyenBan(mTableSelection.getIdPhieu(), mTableSelection.getId(), newTableInfo.getId(), mHandlerMoveTable, true);
                        mSelectedTNewTable = msg.arg1;
                    } else {
                        mTableSelection = (TableInfo) msg.obj;
                        setTotal(mTableSelection.getTotal());
                        mSubMenu.setVisibility(View.GONE);
                        mSelectedTable = msg.arg1;
                    }
                    isMoveTable = false;
                    mAdapterTable.setIsMoveTable(isMoveTable);
                    break;
                case Constants.HANDLER_OPEN_SUB_MENU:
                    if(isMoveTable) {
                        new DialogUtiils().showDialog(mContext, getString(R.string.validate_chuyen_ban), false);
                    } else {
                        mTableSelection = (TableInfo) msg.obj;
                        setTotal(mTableSelection.getTotal());
                        mSubMenu.setVisibility(View.VISIBLE);
                        mSelectedTable = msg.arg1;
                    }
                    isMoveTable = false;
                    mAdapterTable.setIsMoveTable(isMoveTable);
                    break;
                case Constants.HANDLER_OPEN_TABLE:
                    mTableSelection = (TableInfo) msg.obj;
                    if(mTableSelection.getIdPhieu() < 1) {
                        mSaleHelper.getIdPhieu(mTableSelection.getAreaId(),
                                mTableSelection.getId(), mHandlerGetIdPhieu, true);
//                        new SaleAPIHelper().getMaPhieu(mContext, mHandlerGetMaPhieu, true);
                    } else {
                        Intent iProduct = new Intent(mContext, ProductActivity.class);
                        iProduct.putExtra(Constants.EXTRA_TABLE, mTableSelection);
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
            mSubMenu.setVisibility(View.GONE);
            mSelectedTable = -1;
            mSelectedArea = pos;
            if(mSelectedArea < mLstAreas.size()) {
                loadTableData(mLstAreas.get(mSelectedArea).getId());
            }
        }
    };
    
    //If seleeted table's id is null when user tap button "View Order" will process to open table, then order new product
    // else open "View Order" list
    @Override
    protected void processViewOrder() {
        if(mTableSelection.getIdPhieu() < 1){
            mSaleHelper.getIdPhieu(mTableSelection.getAreaId(),
                    mTableSelection.getId(), mHandlerGetIdPhieu, true);
        } else {
            super.processViewOrder();
        }
    }
}
