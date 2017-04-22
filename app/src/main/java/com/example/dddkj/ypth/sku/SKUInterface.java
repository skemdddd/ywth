package com.example.dddkj.ypth.sku;

/**
 * Created by 胡逸枫 on 2017/1/16.
 */

public interface SKUInterface {
    /**
     * 选中属性
     * @param attr
     */
    void selectedAttribute(String[] attr);


    /**
     * 选中id
     * @param id
     */
    void selectedAttribute(String id, String price, String stock);


    /**
     *
     * 图片
     * @param pic
     */
    void selectedAttribute(String pic);

    /**
     * 取消属性选择
     * @param attr
     */
    void uncheckAttribute(String[] attr);

    void uncheckAttribute(boolean empty);
}
