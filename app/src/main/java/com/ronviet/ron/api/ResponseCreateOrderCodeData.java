package com.ronviet.ron.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 1/8/2017.
 */

public class ResponseCreateOrderCodeData extends ResponseCommon {

    public Order data;

    public class Order {
        @SerializedName("order_code")
        public String orderCode;
    }
}
