package com.example.dddkj.ypth.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dddkj.ypth.Entity.GroupInfo;
import com.example.dddkj.ypth.Entity.ProductInfo;
import com.example.dddkj.ypth.Interfaces.OrderInterface;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/25 14:33
 */

public class OrderGoodsAdapter extends BaseQuickAdapter<GroupInfo, BaseViewHolder> {


    Map<String, List<ProductInfo>> childrenTransmit;

    private double smallPrice = 0.00;// 购买的商品小计
    private DecimalFormat df;
    private OrderInterface mOrderInterface;



    public OrderGoodsAdapter(int layoutResId, List<GroupInfo> data, Map<String, List<ProductInfo>> childrenTransmit) {
        super(layoutResId, data);
        this.childrenTransmit = childrenTransmit;
        df = new DecimalFormat("###.00");

    }

    public void setOrderInterface(OrderInterface mOrderInterface) {
        this.mOrderInterface = mOrderInterface;
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupInfo item) {
        helper.setText(R.id.tv_source_name, item.getName());
        EditText editText =helper.getView(R.id.message_tv);
        RecyclerView mRecyclerView = helper.getView(R.id.productList_rv);
        List<ProductInfo> productInfo = childrenTransmit.get(item.getId());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mRecyclerView.setHasFixedSize(true);
        OrderGoodsChildAdapter mOrderGoodsAdapter = new OrderGoodsChildAdapter(R.layout.item_order_goods_child, productInfo);
        mOrderGoodsAdapter.setOrderInterface(mOrderInterface);
        mRecyclerView.setAdapter(mOrderGoodsAdapter);
        for (int i = 0; i < productInfo.size(); i++) {
            smallPrice += productInfo.get(i).getPrice() * productInfo.get(i).getCount();
        }

        mOrderInterface.zprice(df.format(smallPrice) + "");

        if (smallPrice >= Double.valueOf(item.getDeliveryFreeMoney())) {
            helper.setText(R.id.postage_tv, "包邮");
            mOrderInterface.yunfei(0 + "");
        } else {
            helper.setText(R.id.postage_tv,item.getDeliveryMoney() );
//            Logger.i("123"+item.getDeliveryMoney());
            smallPrice+=Double.parseDouble(item.getDeliveryMoney());
            mOrderInterface.yunfei(item.getDeliveryMoney());
        }
        helper.setText(R.id.totalPrice, "￥" + df.format(smallPrice) + "");
        smallPrice = 0.00;

        mOrderInterface.content(editText);
    }


}
