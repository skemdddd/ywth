package com.example.dddkj.ypth.Entity;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/18 16:13
 */

public class AttrVal {
    private String id;

    private String attrVal;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    private  String picurl;
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setAttrVal(String attrVal){
        this.attrVal = attrVal;
    }
    public String getAttrVal(){
        return this.attrVal;
    }
}
