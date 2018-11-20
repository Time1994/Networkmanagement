package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

public class RankBean implements Serializable {
    private String xs_order_count;
    private String xs_order_price;
    private String zl_order_count;
    private String zl_order_price;
    private String wx_order_count;
    private String wx_order_price;
    private String qk_person_count;

    public String getXs_order_count() {
        return xs_order_count;
    }

    public void setXs_order_count(String xs_order_count) {
        this.xs_order_count = xs_order_count;
    }

    public String getXs_order_price() {
        return xs_order_price;
    }

    public void setXs_order_price(String xs_order_price) {
        this.xs_order_price = xs_order_price;
    }

    public String getZl_order_count() {
        return zl_order_count;
    }

    public void setZl_order_count(String zl_order_count) {
        this.zl_order_count = zl_order_count;
    }

    public String getZl_order_price() {
        return zl_order_price;
    }

    public void setZl_order_price(String zl_order_price) {
        this.zl_order_price = zl_order_price;
    }

    public String getWx_order_count() {
        return wx_order_count;
    }

    public void setWx_order_count(String wx_order_count) {
        this.wx_order_count = wx_order_count;
    }

    public String getWx_order_price() {
        return wx_order_price;
    }

    public void setWx_order_price(String wx_order_price) {
        this.wx_order_price = wx_order_price;
    }

    public String getQk_person_count() {
        return qk_person_count;
    }

    public void setQk_person_count(String qk_person_count) {
        this.qk_person_count = qk_person_count;
    }
}
