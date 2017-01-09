package com.ronviet.ron.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LENOVO on 1/8/2017.
 */

public class ProductCatInfo implements Serializable {

    @SerializedName("id_nhom")
    private long id;

    @SerializedName("ten_nhom")
    private String tenNhom;

    @SerializedName("ma_mau")
    private String maMau;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public String getMaMau() {
        return maMau;
    }

    public void setMaMau(String maMau) {
        this.maMau = maMau;
    }
}
