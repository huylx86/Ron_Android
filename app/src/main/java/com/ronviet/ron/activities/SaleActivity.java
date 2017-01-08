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
import com.ronviet.ron.models.AreaInfo;
import com.ronviet.ron.models.TableInfo;
import com.ronviet.ron.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends BaseActivity {

    private RecyclerView mRecyclerTables, mRecyclerAreas;
    private TableRecyclerViewAdapter mAdapterTable;
    private AreaRecyclerViewAdapter mAdapterArea;
    private View mSubMenu;
    private List<TableInfo> mLstTables;
    private List<AreaInfo> mLstAreas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        mContext = this;
        initLayout();
    }

    private void initLayout()
    {
        LinearLayout lnOrder = (LinearLayout)findViewById(R.id.ln_order);
        lnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SaleActivity.this, OrderActivity.class));
            }
        });
        mRecyclerTables = (RecyclerView) findViewById(R.id.recycler_view_tables);
        mRecyclerAreas = (RecyclerView) findViewById(R.id.recycler_view_area);
        mSubMenu = findViewById(R.id.ln_sub_menu);

        dummyAreaTableData();

        mLstTables = mLstAreas.get(0).getmLstTables();

        GridLayoutManager tableLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerTables.setLayoutManager(tableLayoutManager);
        mAdapterTable = new TableRecyclerViewAdapter(this, mLstTables, mHandlerSubMenu);
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

//    private void dummyTableData()
//    {
//        mLstTables = new ArrayList<>();
//        for(int i=0;i<5;i++)
//        {
//            TableInfo info = new TableInfo();
//            info.setName(String.valueOf(i));
//            info.setOrder(true);
//            if(i%2 == 0) {
//                info.setOrder(false);
//            }
//            info.setSelection(false);
//            mLstTables.add(info);
//        }
//    }

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
            List<TableInfo> tables = new ArrayList<>();
            for(int j=0;j<5;j++)
            {
                TableInfo info = new TableInfo();
                info.setName("Khu " + i +  " - " + String.valueOf(i));
                info.setOrder(true);
                if(j%2 == 0) {
                    info.setOrder(false);
                }
                info.setSelection(false);
                tables.add(info);
            }
            area.setmLstTables(tables);
            mLstAreas.add(area);
        }
    }

    protected Handler mHandlerSubMenu = new Handler() {
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
            }
        }
    };

    protected Handler mHandlerAreaSelection = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos = msg.what;
            mLstTables = mLstAreas.get(pos).getmLstTables();
            mAdapterTable.updateData(mLstTables);
        }
    };
}
