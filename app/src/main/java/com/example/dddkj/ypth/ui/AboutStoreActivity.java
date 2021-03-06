package com.example.dddkj.ypth.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.dddkj.ypth.Base.BaseActivity;
import com.example.dddkj.ypth.Entity.ShopAbout;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;
import com.example.dddkj.ypth.common.RequesURL;
import com.example.dddkj.ypth.utils.ProgressActivity;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 项目名称：亿我同行
 * <店铺介绍>
 * 创建时间：2017/3/16 15:54
 */

public class AboutStoreActivity extends BaseActivity {
    @BindView(R.id.shoplogo_iv)
    ImageView shoplogo;
    @BindView(R.id.shopNname_iv)
    TextView shopname;
    @BindView(R.id.favoritesnum_tv)
    TextView favoritesnum;
    @BindView(R.id.ordernum_tv)
    TextView ordernum;
    @BindView(R.id.goodsScoreNum_tv)
    TextView goodsScoreNum;
    @BindView(R.id.serviceScoreNum_tv)
    TextView serviceScoreNum;
    @BindView(R.id.timeScoreNum_tv)
    TextView timeScoreNum;
    @BindView(R.id.content_tv)
    TextView content;
    @BindView(R.id.ctime_tv)
    TextView ctime;
    @BindView(R.id.area_tv)
    TextView area_tv;
    Intent mIntent;
    @BindView(R.id.title_back)
    ImageView title_back;
    @BindView(R.id.ProgressActivity)
    ProgressActivity mProgressActivity;
    @BindView(R.id.phone_rlyt)
    RelativeLayout phone_rlyt;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_about_store);
        mIntent = getIntent();
    }

    @Override
    protected void setListener() {
        initView();
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().finishActivity(AboutStoreActivity.this);
            }
        });
    }

    protected void initView() {

    }

    @Override
    protected void Request() {
        final Gson gson = new Gson();
        OkGo.post(RequesURL.SHOPABOUT)
                .tag(this)
                .params("shopid", mIntent.getStringExtra("shopid"))
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        mProgressActivity.showLoading();
                    }

                    @Override
                    public void onAfter(String s, Exception e) {
                        super.onAfter(s, e);
                        mProgressActivity.showContent();
                        final ShopAbout shopAbout = gson.fromJson(s, ShopAbout.class);
                        Glide.with(MyApplication.getInstance())
                                .load(RequesURL.URL + shopAbout.getData().getShoplogo())
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        shoplogo.setImageBitmap(resource);
                                    }
                                });
                        shopname.setText(shopAbout.getData().getShopname());
                        favoritesnum.setText(shopAbout.getData().getFavoritesnum());
                        ordernum.setText(shopAbout.getData().getOrdernum());
                        goodsScoreNum.setText(shopAbout.getData().getGoodsscorenum());
                        serviceScoreNum.setText(shopAbout.getData().getServicescorenum());
                        timeScoreNum.setText(shopAbout.getData().getTimescorenum());
                        content.setText(shopAbout.getData().getContent());
                        ctime.setText(shopAbout.getData().getCtime());
                        area_tv.setText(shopAbout.getData().getArea());
                        phone_rlyt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shopAbout.getData().getTel()));
                                if (ActivityCompat.checkSelfPermission(AboutStoreActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                    }


                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Logger.json(s);


                    }
                });
    }

    @Override
    protected Context getActivityContext() {
        return MyApplication.getInstance();
    }

    @Override
    public void onClick(View v) {

    }
}
