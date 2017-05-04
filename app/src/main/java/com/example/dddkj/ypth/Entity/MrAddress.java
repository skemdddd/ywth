package com.example.dddkj.ypth.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/25 12:36
 */

public class MrAddress implements Serializable {
    private String code;

    private String message;
    @SerializedName("data")
    private MrAddressData data;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(MrAddressData data) {
        this.data = data;
    }

    public MrAddressData getData() {
        return this.data;
    }

}
