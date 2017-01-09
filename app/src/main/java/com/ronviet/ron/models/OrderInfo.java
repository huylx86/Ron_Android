package com.ronviet.ron.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 1/7/2017.
 */

public class OrderInfo extends ProductInfo {
    @SerializedName("order_id")
    private long orderId;

    @SerializedName("order_code")
    private String orderCode;

    @SerializedName("order_id_chi_tiet")
    private String orderChiTietPhieu;

    @SerializedName("so_luong")
    private float soLuong;

    private float total;
    private String promotion;
//    private boolean isReturnProd;


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderChiTietPhieu() {
        return orderChiTietPhieu;
    }

    public void setOrderChiTietPhieu(String orderChiTietPhieu) {
        this.orderChiTietPhieu = orderChiTietPhieu;
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

    public float getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(float soLuong) {
        this.soLuong = soLuong;
    }
}
