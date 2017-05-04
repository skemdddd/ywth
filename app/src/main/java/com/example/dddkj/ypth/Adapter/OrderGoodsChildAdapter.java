package com.example.dddkj.ypth.Adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dddkj.ypth.Entity.ProductInfo;
import com.example.dddkj.ypth.Interfaces.OrderInterface;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;
import com.example.dddkj.ypth.common.RequesURL;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/25 15:27
 */

public class OrderGoodsChildAdapter extends BaseQuickAdapter<ProductInfo, BaseViewHolder> {
    private OrderInterface mOrderInterface;
    public OrderGoodsChildAdapter(int layoutResId, List<ProductInfo> data) {
        super(layoutResId, data);

    }
    public void setOrderInterface(OrderInterface mOrderInterface) {
        this.mOrderInterface = mOrderInterface;

    }

    @Override
    protected void convert(BaseViewHolder helper, ProductInfo item) {
        helper.setText(R.id.goodsName, item.getDesc());
        helper.setText(R.id.goodsVal,item.getGoodsVal());
        helper.setText(R.id.shopPrice,"￥"+item.getPrice()+"");
        helper.setText(R.id.goodsCnt,"×"+item.getCount());
        helper.setText(R.id.marketPrice,item.getMarketPrice());
        Glide.with(MyApplication.getInstance())
                .load(RequesURL.URL+item.getImageUrl())
                .asBitmap()
                .into((ImageView) helper.getView(R.id.goodsImg));


        mOrderInterface.goodsNum(item.getCount()+"");
        mOrderInterface.goodsid(item.getId()+"");
        mOrderInterface.goodsPrice(item.getPrice()+"");


    }
}
