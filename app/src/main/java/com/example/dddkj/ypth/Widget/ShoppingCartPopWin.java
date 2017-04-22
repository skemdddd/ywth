package com.example.dddkj.ypth.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.dddkj.ypth.Entity.GoogsNews;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;
import com.example.dddkj.ypth.common.RequesURL;
import com.example.dddkj.ypth.sku.GoodsAttrsAdapter;
import com.example.dddkj.ypth.sku.SKUInterface;
import com.example.dddkj.ypth.utils.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.mrj.library.RoundImageView;

import okhttp3.Call;
import okhttp3.Response;
import ren.qinc.numberbutton.NumberButton;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/10 9:15
 */

public class ShoppingCartPopWin extends PopupWindow implements SKUInterface {
    private View view;


    private RecyclerView rv_sku;

    private GoodsAttrsAdapter mAdapter;

    private GoogsNews googsNews;
    private NumberButton number_button;
    private TextView goodsStock_tv;
    private TextView goodsPrice_tv;
    private RoundImageView Log_img;
    private String goodsid;
    Context mContext;

    public ShoppingCartPopWin(final Context mContext, final GoogsNews googsNews) {
        this.googsNews=googsNews;
        this.mContext=mContext;
        goodsid="";
        this.view = LayoutInflater.from(mContext).inflate(R.layout.shopping_cart_goods_attribute, null);
        ImageView btn_cancel = (ImageView) view.findViewById(R.id.back_img);
        goodsStock_tv = (TextView) view.findViewById(R.id.goodsStock_tv);
        goodsPrice_tv = (TextView) view.findViewById(R.id.goodsPrice_tv);
        Log_img = (RoundImageView) view.findViewById(R.id.Log_img);
        Button Shopping_cart = (Button) view.findViewById(R.id.Shopping_cart);
        number_button= (NumberButton) view.findViewById(R.id.number_button);
        number_button.setCurrentNumber(1);
        Shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodsid.equals("")){
                    T.showShort(mContext,"请选择商品属性");
                }else{
                    dismiss();
                    T.showShort(mContext,"已加入购物车");
                    OkGo.post(RequesURL.CARTINSETR)
                            .tag(this)
                            .params("uid", MyApplication.getInstance().getUserid())
                            .params("goodsid", googsNews.getData().getGId())
                            .params("num",number_button.getNumber())
                            .params("goods_attrval",goodsid)
                            .cacheKey("cacheKey")
                            .cacheMode(CacheMode.DEFAULT)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                }
                            });
                }

            }
        });

        goodsStock_tv.setText("库存" + googsNews.getData().getAttrList().getZstock());
        goodsPrice_tv.setText("￥" + googsNews.getData().getAttrList().getAttrPrice());
        Glide.with(MyApplication.getInstance())
                .load(RequesURL.URL + googsNews.getData().getAttrList().getPicurl())
                .asBitmap()
                .skipMemoryCache(false)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log_img.setImageBitmap(resource);
                    }
                });
        rv_sku = (RecyclerView) view.findViewById(R.id.sku_rv);
        mAdapter = new GoodsAttrsAdapter(mContext, googsNews.getData().getAttrList().getAttributes(), googsNews.getData().getAttrList().getStockGoods());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_sku.setLayoutManager(layoutManager);
        rv_sku.setFocusable(false);
        mAdapter.setSKUInterface(this);
        rv_sku.setAdapter(mAdapter);

        // 取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });


        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);


    }


    @Override
    public void selectedAttribute(String[] attr) {

    }

    @Override
    public void selectedAttribute(String id, String price, String stock) {
        goodsid=id;
        number_button.setInventory(Integer.parseInt(stock));
        number_button.setOnWarnListener(new NumberButton.OnWarnListener() {
            @Override
            public void onWarningForInventory(int inventory) {
                T.showShort(mContext,"当前库存:" + inventory);
            }

            @Override
            public void onWarningForBuyMax(int max) {

            }
        });
        goodsStock_tv.setText("库存" + stock);
        goodsPrice_tv.setText("￥" + price);

    }

    @Override
    public void selectedAttribute(String pic) {
        Glide.with(MyApplication.getInstance())
                .load(RequesURL.URL + pic)
                .asBitmap()
                .skipMemoryCache(false)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log_img.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public void uncheckAttribute(String[] attr) {

    }

    @Override
    public void uncheckAttribute(boolean empty) {
        goodsid="";
        if(empty){
            goodsStock_tv.setText("库存" + googsNews.getData().getAttrList().getZstock());
            goodsPrice_tv.setText("￥" + googsNews.getData().getAttrList().getAttrPrice());
            Glide.with(MyApplication.getInstance())
                    .load(RequesURL.URL + googsNews.getData().getAttrList().getPicurl())
                    .asBitmap()
                    .skipMemoryCache(false)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Log_img.setImageBitmap(resource);
                        }
                    });
        }
    }
}
