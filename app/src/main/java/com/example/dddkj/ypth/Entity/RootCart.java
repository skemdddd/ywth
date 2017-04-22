package com.example.dddkj.ypth.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/19 16:16
 */

public class RootCart {
    private String code;

    private String message;
   @SerializedName("data")
    private List<DataCart> data ;

    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setData(List<DataCart> data){
        this.data = data;
    }
    public List<DataCart> getData(){
        return this.data;
    }

}