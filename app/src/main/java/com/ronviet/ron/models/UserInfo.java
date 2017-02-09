package com.ronviet.ron.models;

/**
 * Created by LENOVO on 2/9/2017.
 */

import com.google.gson.annotations.SerializedName;


public class UserInfo {

    @SerializedName("nV_AUTOID")
    private long nv_autoid;

    @SerializedName("uS_USERNAME")
    private String us_username;

    @SerializedName("uS_THETU")
    private String us_password;

    @SerializedName("nV_TENNV")
    private String nv_tennv;

//    private boolean us_dacbiet;
//    private boolean us_kichhoat;
//    private long nv_autoid;
//    private long nnv_autoid;
//    private String us_ghi_chu;
//    private boolean us_public;
//    private String us_thetu;
//    private String us_nguoitao;
//    private String us_ngay_tao;


    public long getNv_autoid() {
        return nv_autoid;
    }

    public void setNv_autoid(long nv_autoid) {
        this.nv_autoid = nv_autoid;
    }

    public String getUs_username() {
        return us_username;
    }

    public void setUs_username(String us_username) {
        this.us_username = us_username;
    }

    public String getUs_password() {
        return us_password;
    }

    public void setUs_password(String us_password) {
        this.us_password = us_password;
    }

    public String getNv_tennv() {
        return nv_tennv;
    }

    public void setNv_tennv(String nv_tennv) {
        this.nv_tennv = nv_tennv;
    }
}
