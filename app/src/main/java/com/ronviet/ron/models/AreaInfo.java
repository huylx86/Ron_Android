package com.ronviet.ron.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hle59 on 1/6/2017.
 */

public class AreaInfo {

    @SerializedName("id")
    private long id;

    @SerializedName("ten")
    private String name;

    private boolean mIsSelection = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean ismIsSelection() {
        return mIsSelection;
    }

    public void setmIsSelection(boolean mIsSelection) {
        this.mIsSelection = mIsSelection;
    }
}
