package com.example.dddkj.ypth.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dddkj.ypth.Entity.ShopPageCouponList;
import com.example.dddkj.ypth.R;

import java.util.List;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/3/16 8:53
 */

public class StoreCouponAdapter extends BaseQuickAdapter<ShopPageCouponList,BaseViewHolder> {


    public StoreCouponAdapter(int layoutResId, List<ShopPageCouponList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopPageCouponList item) {
            helper.setText(R.id.jprice_tv,item.getJprice());
            helper.setText(R.id.mprice_tv,item.getMprice());
    }
}
