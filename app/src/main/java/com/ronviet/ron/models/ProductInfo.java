package com.ronviet.ron.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LENOVO on 1/7/2017.
 */

public class ProductInfo implements Serializable{
    @SerializedName("id_mon")
    private long id;

    @SerializedName("ten_mon")
    private String tenMon;

    @SerializedName("ma_mon")
    private String maMon;

    @SerializedName("ma_mau")
    private String maMau;

    @SerializedName("don_vi_tinh")
    private String donViTinh;

    @SerializedName("don_vi_tinh_id")
    private long donViTinhId;

    @SerializedName("gia_goc")
    private float giaGoc;

    @SerializedName("don_gia")
    private float donGia;

    @SerializedName("gia_co_thue")
    private boolean isGiaCoThue;

    @SerializedName("thue")
    private float thue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getMaMau() {
        return maMau;
    }

    public void setMaMau(String maMau) {
        this.maMau = maMau;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public long getDonViTinhId() {
        return donViTinhId;
    }

    public void setDonViTinhId(long donViTinhId) {
        this.donViTinhId = donViTinhId;
    }

    public float getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(float giaGoc) {
        this.giaGoc = giaGoc;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public boolean isGiaCoThue() {
        return isGiaCoThue;
    }

    public void setGiaCoThue(boolean giaCoThue) {
        isGiaCoThue = giaCoThue;
    }

    public float getThue() {
        return thue;
    }

    public void setThue(float thue) {
        this.thue = thue;
    }
}
