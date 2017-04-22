package com.example.dddkj.ypth.Entity;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/18 16:14
 */

public class StockGoods {
    private String goodsID;
    private String stock;

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;

    private List<GoodsInfo> goodsInfo ;

    public void setGoodsID(String goodsID){
        this.goodsID = goodsID;
    }
    public String getGoodsID(){
        return this.goodsID;
    }
    public void setGoodsInfo(List<GoodsInfo> goodsInfo){
        this.goodsInfo = goodsInfo;
    }
    public List<GoodsInfo> getGoodsInfo(){
        return this.goodsInfo;
    }
}
