package com.example.dddkj.ypth.Entity;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/18 16:13
 */

public class AttrList {
    private String picurl;

    private String zstock;

    private String attrPrice;

    private List<Attributes> attributes ;

    private List<StockGoods> stockGoods ;

    public void setPicurl(String picurl){
        this.picurl = picurl;
    }
    public String getPicurl(){
        return this.picurl;
    }
    public void setZstock(String zstock){
        this.zstock = zstock;
    }
    public String getZstock(){
        return this.zstock;
    }
    public void setAttrPrice(String attrPrice){
        this.attrPrice = attrPrice;
    }
    public String getAttrPrice(){
        return this.attrPrice;
    }
    public void setAttributes(List<Attributes> attributes){
        this.attributes = attributes;
    }
    public List<Attributes> getAttributes(){
        return this.attributes;
    }
    public void setStockGoods(List<StockGoods> stockGoods){
        this.stockGoods = stockGoods;
    }
    public List<StockGoods> getStockGoods(){
        return this.stockGoods;
    }

}
