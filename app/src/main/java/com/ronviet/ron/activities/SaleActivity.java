package com.ronviet.ron.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ronviet.ron.R;
import com.ronviet.ron.adapters.AreaRecyclerViewAdapter;
import com.ronviet.ron.adapters.TableRecyclerViewAdapter;
import com.ronviet.ron.models.AreaInfo;
import com.ronviet.ron.models.TableInfo;

import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerTables, mRecyclerAreas;
    private TableRecyclerViewAdapter mAdapterTable;
    private AreaRecyclerViewAdapter mAdapterArea;
    private List<TableInfo> mLstTables;
    private List<AreaInfo> mLstAreas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        initLayout();
    }

    private void initLayout()
    {
        mRecyclerTables = (RecyclerView) findViewById(R.id.recycler_view_tables);
        mRecyclerAreas = (RecyclerView) findViewById(R.id.recycler_view_area);

        dummyTableData();
        dummyAreaData();

        GridLayoutManager tableLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerTables.setLayoutManager(tableLayoutManager);
        mAdapterTable = new TableRecyclerViewAdapter(this, mLstTables);
        mRecyclerTables.setAdapter(mAdapterTable);

        LinearLayoutManager areaLayoutManager = new LinearLayoutManager(this);
        areaLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerAreas.setLayoutManager(areaLayoutManager);
        mAdapterArea = new AreaRecyclerViewAdapter(this, mLstAreas);
        mRecyclerAreas.setAdapter(mAdapterArea);


    }

    private void dummyTableData()
    {
        mLstTables = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            TableInfo info = new TableInfo();
            info.setName(String.valueOf(i));
            mLstTables.add(info);
        }
    }

    private void dummyAreaData()
    {
        mLstAreas = new ArrayList<>();
        for(int i = 0 ;i<5; i++){
            AreaInfo info = new AreaInfo();
            info.setName(String.valueOf("Khu" + i));
            mLstAreas.add(info);
        }
    }
}
