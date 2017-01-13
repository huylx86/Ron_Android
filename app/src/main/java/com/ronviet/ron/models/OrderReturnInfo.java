package com.ronviet.ron.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 1/7/2017.
 */

public class OrderReturnInfo extends ProductInfo {
    @SerializedName("id_tra_hang")
    private long idTraHang;

    @SerializedName("id_phieu")
    private long idPhieu;

    @SerializedName("id_chi_tiet_phieu")
    private long idChiTietPhieu;

    @SerializedName("order_id")
    private long orderId;

    @SerializedName("id_ban")
    private long idBan;

    @SerializedName("pc_id")
    private long pcId;

    @SerializedName("so_luong")
    private float soLuong;

    private float soLuongTra = 0;
    private float total;
    private String promotion;
//    private boolean isReturnProd;


    public long getIdTraHang() {
        return idTraHang;
    }

    public void setIdTraHang(long idTraHang) {
        this.idTraHang = idTraHang;
    }

    public long getIdPhieu() {
        return idPhieu;
    }

    public void setIdPhieu(long idPhieu) {
        this.idPhieu = idPhieu;
    }

    public long getIdChiTietPhieu() {
        return idChiTietPhieu;
    }

    public void setIdChiTietPhieu(long idChiTietPhieu) {
        this.idChiTietPhieu = idChiTietPhieu;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getIdBan() {
        return idBan;
    }

    public void setIdBan(long idBan) {
        this.idBan = idBan;
    }

    public long getPcId() {
        return pcId;
    }

    public void setPcId(long pcId) {
        this.pcId = pcId;
    }

    public float getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(float soLuong) {
        this.soLuong = soLuong;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public float getSoLuongTra() {
        return soLuongTra;
    }

    public void setSoLuongTra(float soLuongTra) {
        this.soLuongTra = soLuongTra;
    }
}
