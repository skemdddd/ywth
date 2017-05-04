package com.example.dddkj.ypth.Entity;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/19 16:14
 */

public class ListCart {
    private String goodsId;

    private String goodsName;

    private String shopPrice;

    private String marketPrice;

    private String goodsImg;

    private String goodsCnt;

    private String goodsVal;

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    private String stock;

    public void setGoodsId(String goodsId){
        this.goodsId = goodsId;
    }
    public String getGoodsId(){
        return this.goodsId;
    }
    public void setGoodsName(String goodsName){
        this.goodsName = goodsName;
    }
    public String getGoodsName(){
        return this.goodsName;
    }
    public void setShopPrice(String shopPrice){
        this.shopPrice = shopPrice;
    }
    public String getShopPrice(){
        return this.shopPrice;
    }
    public void setMarketPrice(String marketPrice){
        this.marketPrice = marketPrice;
    }
    public String getMarketPrice(){
        return this.marketPrice;
    }
    public void setGoodsImg(String goodsImg){
        this.goodsImg = goodsImg;
    }
    public String getGoodsImg(){
        return this.goodsImg;
    }
    public void setGoodsCnt(String goodsCnt){
        this.goodsCnt = goodsCnt;
    }
    public String getGoodsCnt(){
        return this.goodsCnt;
    }
    public void setGoodsVal(String goodsVal){
        this.goodsVal = goodsVal;
    }
    public String getGoodsVal(){
        return this.goodsVal;
    }

}