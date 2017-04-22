package com.example.dddkj.ypth.Entity;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/18 16:13
 */

public class Attributes {
    private String attrId;

    private String attrName;

    private List<AttrVal> attrVal ;

    public void setAttrId(String attrId){
        this.attrId = attrId;
    }
    public String getAttrId(){
        return this.attrId;
    }
    public void setAttrName(String attrName){
        this.attrName = attrName;
    }
    public String getAttrName(){
        return this.attrName;
    }
    public void setAttrVal(List<AttrVal> attrVal){
        this.attrVal = attrVal;
    }
    public List<AttrVal> getAttrVal(){
        return this.attrVal;
    }
}
