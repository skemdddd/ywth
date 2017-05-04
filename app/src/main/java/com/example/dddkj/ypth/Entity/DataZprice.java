package com.example.dddkj.ypth.Entity;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/5/2 11:13
 */

public class DataZprice {
    public List<String> getShopid() {
        return shopid;
    }

    public void setShopid(List<String> shopid) {
        this.shopid = shopid;
    }

    public List<String> getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(List<String> goodsid) {
        this.goodsid = goodsid;
    }

    public List<String> getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(List<String> goodsNum) {
        this.goodsNum = goodsNum;
    }

    public List<String> getZprice() {
        return zprice;
    }

    public void setZprice(List<String> zprice) {
        this.zprice = zprice;
    }

    public List<String> getYunfei() {
        return yunfei;
    }

    public void setYunfei(List<String> yunfei) {
        this.yunfei = yunfei;
    }

    public String getAdrId() {
        return adrId;
    }

    public void setAdrId(String adrId) {
        this.adrId = adrId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private List<String> shopid;

    private List<String> goodsid;

    private List<String> goodsNum;

    private List<String> zprice;

    private List<String> yunfei;

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    private List<String> content;

    public List<String> getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(List<String> goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    private List<String> goodsPrice;

    private String adrId;

    private String uid;


}
