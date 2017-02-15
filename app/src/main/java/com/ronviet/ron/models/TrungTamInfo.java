package com.ronviet.ron.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LENOVO on 2/14/2017.
 */

public class TrungTamInfo implements Serializable {

    @SerializedName("id_trungtam")
    long id;

    @SerializedName("ten_trungtam")
    String name;

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
}
