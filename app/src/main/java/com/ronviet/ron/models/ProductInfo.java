package com.ronviet.ron.models;

import java.io.Serializable;

/**
 * Created by LENOVO on 1/7/2017.
 */

public class ProductInfo implements Serializable{
    private long id;
    private String name;
    private String des;
    private String price;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
