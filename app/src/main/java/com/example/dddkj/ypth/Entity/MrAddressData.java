package com.example.dddkj.ypth.Entity;

import java.io.Serializable;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/25 12:37
 */

public class MrAddressData implements Serializable{
    private String adrId;

    private String uName;

    private String uTel;

    private String cityAddress;

    public void setAdrId(String adrId){
        this.adrId = adrId;
    }
    public String getAdrId(){
        return this.adrId;
    }
    public void setUName(String uName){
        this.uName = uName;
    }
    public String getUName(){
        return this.uName;
    }
    public void setUTel(String uTel){
        this.uTel = uTel;
    }
    public String getUTel(){
        return this.uTel;
    }
    public void setCityAddress(String cityAddress){
        this.cityAddress = cityAddress;
    }
    public String getCityAddress(){
        return this.cityAddress;
    }

}
