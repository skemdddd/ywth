package com.example.dddkj.ypth.Fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.dddkj.ypth.Adapter.TertiaryDetailsAdapter;
import com.example.dddkj.ypth.Base.BaseFragment;
import com.example.dddkj.ypth.Entity.ShopAll;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;
import com.example.dddkj.ypth.common.Constant;
import com.example.dddkj.ypth.common.RequesURL;
import com.example.dddkj.ypth.ui.MerchandiseNewsActivity;
import com.example.dddkj.ypth.utils.ProgressActivity;
import com.example.dddkj.ypth.utils.VerticalSpaceItemDecoration;
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
 * <p>
 * 创建时间：2017/3/17 9:09
 */

public class ShopSalesPromotionFragment extends BaseFragment {
    @BindView(R.id.shopSales_rv)
    RecyclerView shopSalesList;
    @BindView(R.id.ProgressActivity)
    ProgressActivity mProgressActivity;
    TertiaryDetailsAdapter mTertiaryDetailsAdapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_shop_sales_promotion,container,false);
    }

    @Override
    protected void initListener() {
        initView();

    }

    @Override
    protected void initData() {

    }
    protected void initView(){
        shopSalesList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        shopSalesList.setLayoutManager(new GridLayoutManager(MyApplication.getInstance(), 2));
        shopSalesList.setHasFixedSize(true);
        mTertiaryDetailsAdapter = new TertiaryDetailsAdapter(R.layout.item_home_popularity, null);
        shopSalesList.setAdapter(mTertiaryDetailsAdapter);
        shopSalesList.addItemDecoration(new VerticalSpaceItemDecoration(20,0));
        Submitgoods("hot");

    }
    public void Submitgoods(String type) {
        final Gson gson = new Gson();
        OkGo.post(RequesURL.SHOPGOODSLIST)
                .tag(this)
                .params("shopid", Constant.getShopid())
                .params("flag",type)
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
                        final ShopAll shopAll = gson.fromJson(s, ShopAll.class);
                        mTertiaryDetailsAdapter.setNewData(shopAll.getData());
                        if (shopAll.getData().size() == 0) {
                            mProgressActivity.showEmpty(getResources().getDrawable(R.mipmap.ic_empty_page), "", "咦...没有任何内容，先去逛逛别的吧", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    MyApplication.getInstance().finishActivity(getActivity());
                                }
                            });

                        }
                        shopSalesList.addOnItemTouchListener(new OnItemClickListener() {
                            @Override
                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(getActivity(),MerchandiseNewsActivity.class);
                                intent.putExtra("goodsid",shopAll.getData().get(position).getGId() );
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                    }
                });


    }
    @Override
    public View getScrollableView() {
        return shopSalesList;
    }
}
