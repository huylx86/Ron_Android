package com.ronviet.ron.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 1/8/2017.
 */

public class ResponseCreateMaPhieuData extends ResponseCommon {

    public MaPhieu data;

    public class MaPhieu {
        @SerializedName("ma_phieu")
        public String maPhieu;
    }
}
