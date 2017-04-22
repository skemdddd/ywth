package com.example.dddkj.ypth.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/3/18 9:27
 */

public class ShopAll {
    private String code;
    private String message;
    @SerializedName("data")
    private List<ThirdGoogsData> data;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(List<ThirdGoogsData> data) {
        this.data = data;
    }
    public List<ThirdGoogsData> getData() {
        return data;
    }
}
