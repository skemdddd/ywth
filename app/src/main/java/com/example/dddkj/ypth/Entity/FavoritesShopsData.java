package com.example.dddkj.ypth.Entity;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/3/25 15:33
 */

public class FavoritesShopsData {
    private String shopid;
    private String shopname;
    private String shoplogo;
    private String favoritesnum;
    private String ordernum;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    private String fid;
    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
    public String getShopid() {
        return shopid;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
    public String getShopname() {
        return shopname;
    }

    public void setShoplogo(String shoplogo) {
        this.shoplogo = shoplogo;
    }
    public String getShoplogo() {
        return shoplogo;
    }

    public void setFavoritesnum(String favoritesnum) {
        this.favoritesnum = favoritesnum;
    }
    public String getFavoritesnum() {
        return favoritesnum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }
    public String getOrdernum() {
        return ordernum;
    }
}
