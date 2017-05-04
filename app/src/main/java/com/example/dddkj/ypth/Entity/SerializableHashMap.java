package com.example.dddkj.ypth.Entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/24 15:36
 */

public class SerializableHashMap implements Serializable {

    public Map<String, List<ProductInfo>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<ProductInfo>> map) {
        this.map = map;
    }

    private Map<String, List<ProductInfo>> map;


}