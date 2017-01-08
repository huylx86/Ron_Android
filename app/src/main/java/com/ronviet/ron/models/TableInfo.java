package com.ronviet.ron.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hle59 on 1/6/2017.
 */

public class TableInfo implements Serializable {
    @SerializedName("id_ban")
    private long id;

    @SerializedName("ten_ban")
    private String name;

    @SerializedName("id_phieu")
    private long idPhieu;

    @SerializedName("ma_phieu")
    private String maPhieu;

    @SerializedName("so_lan_in_phieu")
    private long soLanInPhieu;

    @SerializedName("tong_tien")
    private float total;

    private long areaId;
//    private String date;
//    private boolean isOrder;
    private boolean isSelection;
//    private long orderId = 0;


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

    public long getIdPhieu() {
        return idPhieu;
    }

    public void setIdPhieu(long idPhieu) {
        this.idPhieu = idPhieu;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public long getSoLanInPhieu() {
        return soLanInPhieu;
    }

    public void setSoLanInPhieu(long soLanInPhieu) {
        this.soLanInPhieu = soLanInPhieu;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public boolean isSelection() {
        return isSelection;
    }

    public void setSelection(boolean selection) {
        isSelection = selection;
    }
}
