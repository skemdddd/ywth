package com.example.dddkj.ypth.Entity;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/2/28 13:13
 */

public class SecondaryRecommendationData {
    private String gId;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    private String fid;

    private String gName;

    private String price;

    private String warstock;

    private String picurl;

    private String orderNum;

    public String getIsPostage() {
        return isPostage;
    }

    public void setIsPostage(String isPostage) {
        this.isPostage = isPostage;
    }

    private String isPostage;

    public void setGId(String gId) {
        this.gId = gId;
    }

    public String getGId() {
        return this.gId;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }

    public String getGName() {
        return this.gName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return this.price;
    }

    public void setWarstock(String warstock) {
        this.warstock = warstock;
    }

    public String getWarstock() {
        return this.warstock;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getPicurl() {
        return this.picurl;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderNum() {
        return this.orderNum;
    }

}
