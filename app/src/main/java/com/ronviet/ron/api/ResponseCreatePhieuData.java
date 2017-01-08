package com.ronviet.ron.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 1/8/2017.
 */

public class ResponseCreatePhieuData extends ResponseCommon {

    public Phieu data;

    public class Phieu {
        @SerializedName("id_phieu")
        public String idPhieu;
    }
}
