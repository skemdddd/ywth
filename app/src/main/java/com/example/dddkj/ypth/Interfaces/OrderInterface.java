package com.example.dddkj.ypth.Interfaces;

import android.widget.EditText;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/5/2 10:18
 */

public interface OrderInterface {
    /**
     * 商品总价
     *
     * @param zprice
     */
    void zprice(String zprice);

    /**
     * 运费
     *
     * @param yunfei
     */
    void yunfei(String yunfei);

    /**
     * 购买数量
     *
     * @param goodsNum
     */
    void goodsNum(String goodsNum);

    /**
     * 商品ID
     *
     * @param goodsid
     */
    void goodsid(String goodsid);

    /**
     * 商品单价
     *
     * @param goodsPrice
     */
    void goodsPrice(String goodsPrice);


    /**
     * 备注
     *
     * @param editText
     */
    void content(EditText editText);

}
