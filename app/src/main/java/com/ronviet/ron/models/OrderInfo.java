package com.ronviet.ron.models;

/**
 * Created by LENOVO on 1/7/2017.
 */

public class OrderInfo extends ProductInfo {
    private long total;
    private int number;
    private String promotion;
//    private boolean isReturnProd;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

//    public boolean isReturnProd() {
//        return isReturnProd;
//    }
//
//    public void setReturnProd(boolean returnProd) {
//        isReturnProd = returnProd;
//    }
}
