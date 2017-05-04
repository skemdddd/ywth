package com.example.dddkj.ypth.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/19 16:14
 */

public class DataCart {
    private String shopId;

    private String shopName;

    public String getDeliveryFreeMoney() {
        return deliveryFreeMoney;
    }


    public void setDeliveryFreeMoney(String deliveryFreeMoney) {
        this.deliveryFreeMoney = deliveryFreeMoney;
    }

    public String getDeliveryMoney() {
        return deliveryMoney;
    }

    public void setDeliveryMoney(String deliveryMoney) {
        this.deliveryMoney = deliveryMoney;
    }

    private  String deliveryMoney;
    private String deliveryFreeMoney;

    @SerializedName("list")
    public List<ListCart> getList() {
        return list;
    }

    public void setList(List<ListCart> list) {
        this.list = list;
    }

    private List<ListCart> list;

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopId() {
        return this.shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return this.shopName;
    }


}