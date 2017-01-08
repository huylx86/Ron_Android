package com.ronviet.ron.models;

import java.util.List;

/**
 * Created by hle59 on 1/6/2017.
 */

public class AreaInfo {
    private String name;
    private List<TableInfo> mLstTables;
    private boolean mIsSelection;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableInfo> getmLstTables() {
        return mLstTables;
    }

    public void setmLstTables(List<TableInfo> mLstTables) {
        this.mLstTables = mLstTables;
    }

    public boolean ismIsSelection() {
        return mIsSelection;
    }

    public void setmIsSelection(boolean mIsSelection) {
        this.mIsSelection = mIsSelection;
    }
}
