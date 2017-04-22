package com.example.dddkj.ypth.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.dddkj.ypth.Adapter.HomeBGABannerAdapter;
import com.example.dddkj.ypth.Adapter.MerchandiseCommentAdapter;
import com.example.dddkj.ypth.Base.BaseActivity;
import com.example.dddkj.ypth.Entity.GoogsNews;
import com.example.dddkj.ypth.Entity.ShopPageCouponList;
import com.example.dddkj.ypth.MainActivity;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;
import com.example.dddkj.ypth.Widget.CouponsPopWin;
import com.example.dddkj.ypth.Widget.ShoppingCartPopWin;
import com.example.dddkj.ypth.common.Constant;
import com.example.dddkj.ypth.common.RequesURL;
import com.example.dddkj.ypth.utils.LoginState;
import com.example.dddkj.ypth.utils.ProgressActivity;
import com.example.dddkj.ypth.utils.RecycleViewDivider;
import com.google.gson.Gson;
import com.hedgehog.ratingbar.RatingBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.widget.VerticalSlide;
import com.lzy.widget.vertical.VerticalScrollView;
import com.orhanobut.logger.Logger;

import org.apache.http.util.EncodingUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.dddkj.ypth.R.id.webView;


/**
 * 项目名称：亿我同行
 * <分类详情>
 * 创建时间：2017/3/7 11:01
 */

public class MerchandiseNewsActivity extends BaseActivity {
    Intent mIntent;
    @BindView(R.id.iv_good_detai_collect_select)
    ImageView iv_good_detai_collect_select;
    @BindView(R.id.shopping_cart_img)
    ImageView shopping_cart_img;
    @BindView(R.id.title_back)
    ImageView title_back;

    @BindView(R.id.banner)
    BGABanner mBGABanner;
    //    商品名称
    @BindView(R.id.tradename_text)
    TextView TradeName;
    //    商品金额
    @BindView(R.id.amount_text)
    TextView amount;
    //    商品售价
    @BindView(R.id.marketprice_tv)
    TextView marketprice_tv;
    //    商品销量
    @BindView(R.id.sales_volume_tv)
    TextView sales_volume_tv;
    //    快递
    @BindView(R.id.courier_tv)
    TextView courier_tv;
    //    logo
    @BindView(R.id.shoplogo_image)
    ImageView shoplogo_image;
    //  店铺名称
    @BindView(R.id.shopname_tv)
    TextView shopname_tv;
    //   店铺等级
    @BindView(R.id.goods_tv)
    TextView goods_tv;
    @BindView(R.id.service_tv)
    TextView service_tv;
    @BindView(R.id.quality_tv)
    TextView quality_tv;
    @BindView(R.id.ratingbar)
    RatingBar mRatingBar;
    //    评论
    @BindView(R.id.comment_rv)
    RecyclerView mCommentList;
    MerchandiseCommentAdapter mMerchandiseCommentAdapter;
    @BindView(R.id.comment_number_tv)
    TextView comment_number_tv;
    @BindView(R.id.tab)
    TabLayout mTabLayout;
    @BindView(webView)
    WebView mWebView;
    String postData;
    @BindView(R.id.webViewarr)
    WebView mWebViewarr;
    @BindView(R.id.progressActivity)
    ProgressActivity mProgressActivity;
    @BindView(R.id.all_comments_tv)
    TextView mAllComments_tv;
    @BindView(R.id.back_top)
    ImageButton mBackTop;
    @BindView(R.id.verticalSlide)
    VerticalSlide mVerticalSlide;
    @BindView(R.id.scrollview)
    VerticalScrollView TopView;
    @BindView(R.id.enterstore_ibtn)
    ImageButton enterstore_ibtn;
    @BindView(R.id.allshop_ibtn)
    ImageButton allshop_ibtn;
    @BindView(R.id.iv_good_collection_select)
    CheckBox iv_good_collection_select;
    @BindView(R.id.coupons_rlyt)
    RelativeLayout coupons_rlyt;
    @BindView(R.id.tv_good_detail_shop_cart)
    TextView tv_good_detail_shop_cart;
    WindowManager.LayoutParams params;



    boolean click;


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.malldetails_activity);
        mIntent = getIntent();


    }

    @Override
    protected void setListener() {
        initView();
        if (mWebView != null) {
            postData = "goodsid=" + mIntent.getStringExtra("goodsid");
            mWebView.postUrl(RequesURL.GOODSDETAILSWEB, EncodingUtils.getBytes(postData, "base64"));
        }
        title_back.setOnClickListener(this);
        shopping_cart_img.setOnClickListener(this);

        mVerticalSlide.setOnShowNextPageListener(new VerticalSlide.OnShowNextPageListener() {
            @Override
            public void onShowNextPage() {
                mBackTop.setVisibility(View.VISIBLE);
                mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch (tab.getPosition()) {
                            case 0:

                                if (mWebView != null) {
                                    postData = "goodsid=" + mIntent.getStringExtra("goodsid");
                                    mWebView.postUrl(RequesURL.GOODSDETAILSWEB, EncodingUtils.getBytes(postData, "base64"));
                                }

                                mWebViewarr.setVisibility(View.GONE);
                                mWebView.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                if (mWebView != null) {
                                    postData = "goodsid=" + mIntent.getStringExtra("goodsid");
                                    mWebViewarr.postUrl(RequesURL.GOODSDETAILSWEBARRR, EncodingUtils.getBytes(postData, "base64"));
                                }

                                mWebViewarr.setVisibility(View.VISIBLE);
                                mWebView.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });


            }
        });


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mTabLayout.setVisibility(View.VISIBLE);
            }

        });



        mAllComments_tv.setOnClickListener(this);
        mBackTop.setOnClickListener(this);


    }


    public void initView() {
        mWebView.setVerticalScrollBarEnabled(false);
        mWebViewarr.setVerticalScrollBarEnabled(false);
        mTabLayout.addTab(mTabLayout.newTab().setText("图文详情"));
        mTabLayout.addTab(mTabLayout.newTab().setText("产品参数"));
        mCommentList.setLayoutManager(new LinearLayoutManager(getActivityContext()));
        mCommentList.setHasFixedSize(true);
        mCommentList.setNestedScrollingEnabled(false);
        mMerchandiseCommentAdapter = new MerchandiseCommentAdapter(R.layout.item_evaluate_merchandisenews, null);
        mCommentList.setAdapter(mMerchandiseCommentAdapter);
        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.loginBackground), 1);
        mCommentList.addItemDecoration(recycleViewDivider);


    }

    @Override
    protected void Request() {
        final Gson gson = new Gson();
        Logger.i("name" + MyApplication.getInstance().getUserid());
        OkGo.post(RequesURL.GOODSDETAILS)
                .tag(this)
                .params("uid", MyApplication.getInstance().getUserid())
                .params("goodsid", mIntent.getStringExtra("goodsid"))
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
                        Logger.json(s);
                        mProgressActivity.showContent();

                        final GoogsNews googsNews = gson.fromJson(s, GoogsNews.class);

//                        SKU
                        if(googsNews.getData()!=null){

                            tv_good_detail_shop_cart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(LoginState.LoginState()){
                                        Intent intent = new Intent(MerchandiseNewsActivity.this, LoginAcivity.class);
                                        startActivity(intent);

                                    }else{
                                        showPopFormBottomSku(getRootView(MerchandiseNewsActivity.this),googsNews);
                                    }

                                }
                            });
                        }

//                        轮播图
                        Logger.json(s);
                        setBGABanner(googsNews.getData().getString());
                        TradeName.setText(googsNews.getData().getGName());
                        amount.setText(googsNews.getData().getPrice());
                        marketprice_tv.setText("￥" + googsNews.getData().getMarPrice());
                        sales_volume_tv.setText("销量 :   " + googsNews.getData().getOrderNum());
                        courier_tv.setText("快递 :   " + googsNews.getData().getPostAge());


                        click = googsNews.getData().getIsFavorites().equals("1") ? true : false;
                        iv_good_collection_select.setChecked(click);
                        //        优惠券
                        coupons_rlyt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPopFormBottom(getRootView(MerchandiseNewsActivity.this), googsNews.getData().getShopPageCouponLists());
                            }
                        });
//                        店铺
                        shopname_tv.setText(googsNews.getData().getShopname());
                        goods_tv.setText(googsNews.getData().getGoodsNum());
                        service_tv.setText(googsNews.getData().getServiceNum());
                        quality_tv.setText(googsNews.getData().getTimeNum());
//                        RatingBar mRatingBar =findViewById(R.id.ratingbar)
                        mRatingBar.setStar(Integer.parseInt(googsNews.getData().getShopStar()));
                        Glide.with(MyApplication.getInstance())
                                .load(RequesURL.URL + googsNews.getData().getShoplogo())
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        shoplogo_image.setImageBitmap(resource);
                                    }
                                });
//                        收藏
                        iv_good_collection_select.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (LoginState.LoginState()) {
                                    iv_good_collection_select.setChecked(false);
                                    Intent intent = new Intent(MerchandiseNewsActivity.this, LoginAcivity.class);
                                    startActivity(intent);
                                } else {
                                    if (!click) {
                                        new SweetAlertDialog(MerchandiseNewsActivity.this)
                                                .setTitleText("收藏成功")
                                                .show();
                                        click = true;
                                    } else {
                                        new SweetAlertDialog(MerchandiseNewsActivity.this)
                                                .setTitleText("取消成功")
                                                .show();
                                        click = false;
                                    }
                                    OkGo.post(RequesURL.FAVORITESADD)
                                            .tag(this)
                                            .params("uid", MyApplication.getInstance().getUserid())
                                            .params("id", mIntent.getStringExtra("goodsid"))
                                            .params("type", "goods")
                                            .cacheKey("cacheKey")
                                            .cacheMode(CacheMode.DEFAULT)
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(String s, Call call, Response response) {
                                                    Logger.json(s);

                                                }
                                            });
                                }

                            }
                        });

                        enterstore_ibtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentStore = new Intent(MerchandiseNewsActivity.this, EnterStoreActivity.class);
                                intentStore.putExtra("shopid", googsNews.getData().getSId());
                                startActivity(intentStore);
                            }
                        });
                        iv_good_detai_collect_select.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentStore = new Intent(MerchandiseNewsActivity.this, EnterStoreActivity.class);
                                intentStore.putExtra("shopid", googsNews.getData().getSId());
                                startActivity(intentStore);
                            }
                        });
//                        全部商品
                        allshop_ibtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentStore = new Intent(MerchandiseNewsActivity.this, AllShopActivity.class);
                                intentStore.putExtra("shopid", googsNews.getData().getSId());
                                intentStore.putExtra("catsid", "");
                                intentStore.putExtra("title", "全部商品");
                                startActivity(intentStore);
                            }
                        });
                        mMerchandiseCommentAdapter.setNewData(googsNews.getData().getAppraisesList());
                        if (mMerchandiseCommentAdapter.getData().size() == 0) {
                            mMerchandiseCommentAdapter.setEmptyView(R.layout.comment_empty, (ViewGroup) mCommentList.getParent());
                        }
//                        评价
                        comment_number_tv.setText("评价" + "( " + googsNews.getData().getGoodsAppraisesNum().getNum() + " )");

                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {


                    }
                });
    }

    @Override
    protected Context getActivityContext() {
        return MyApplication.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_comments_tv:
                Intent intent = new Intent(MerchandiseNewsActivity.this, AllCommentsActivity.class);
                intent.putExtra("goodsid", mIntent.getStringExtra("goodsid"));
                startActivity(intent);
                break;
            case R.id.back_top:
                mVerticalSlide.goTop(new VerticalSlide.OnGoTopListener() {
                    @Override
                    public void goTop() {
                        mBackTop.setVisibility(View.GONE);
                        TopView.goTop();
                    }
                });
                break;
            case R.id.title_back:
                MyApplication.getInstance().finishActivity(this);
                break;
            case R.id.shopping_cart_img:
                Intent intent1 = new Intent(this,MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Constant.setShopping(true);
                startActivity(intent1);
                break;


        }
    }


    /**
     * 轮播图
     *
     * @param
     */
    public void setBGABanner(List<String> url) {
        List<String> bannerTitle = new ArrayList<String>();
        List<String> bannerImage = new ArrayList<String>();
        for (int i = 0; i < url.size(); i++) {
            bannerImage.add(RequesURL.URL + url.get(i));
        }
        mBGABanner.setAdapter(new HomeBGABannerAdapter(MerchandiseNewsActivity.this));
        mBGABanner.setData(bannerImage, bannerTitle);
    }



    public void showPopFormBottom(View view, List<ShopPageCouponList> shopPageCouponList) {
        CouponsPopWin couponsPopWin = new CouponsPopWin(this, shopPageCouponList);
//        设置Popupwindow显示位置（从底部弹出）
        couponsPopWin.showAtLocation(findViewById(R.id.bottom), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        couponsPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    public void showPopFormBottomSku(View view,GoogsNews googsNews) {


        ShoppingCartPopWin shoppingCartPopWin = new ShoppingCartPopWin(this,googsNews);

//        设置Popupwindow显示位置（从底部弹出）
        shoppingCartPopWin.showAtLocation(findViewById(R.id.bottom), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        shoppingCartPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

}
