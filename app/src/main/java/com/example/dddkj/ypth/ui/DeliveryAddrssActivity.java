package com.example.dddkj.ypth.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dddkj.ypth.Adapter.DeliveryAddressAdapter;
import com.example.dddkj.ypth.Base.BaseActivity;
import com.example.dddkj.ypth.Entity.AddressList;
import com.example.dddkj.ypth.Entity.MrAddress;
import com.example.dddkj.ypth.Entity.MrAddressData;
import com.example.dddkj.ypth.Entity.SelectedBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/3/22 9:31
 */

public class DeliveryAddrssActivity extends BaseActivity {
    DeliveryAddressAdapter mDeliveryAddressAdapter;
    @BindView(R.id.addressList_rv)
    RecyclerView addressList;
    @BindView(R.id.add_address_tv)
    TextView add_address_tv;
    @BindView(R.id.title_back)
    ImageView title_back;
    private MrAddressData mMrAddressData ;
    public ProgressActivity getProgressActivity() {
        return mProgressActivity;
    }

    public void setProgressActivity(ProgressActivity progressActivity) {
        mProgressActivity = progressActivity;
    }

    @BindView(R.id.ProgressActivity)
    ProgressActivity mProgressActivity;


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_delivery_address);
    }

    @Override
    protected void setListener() {
        initView();
        addressList.setLayoutManager(new LinearLayoutManager(this));
        addressList.setHasFixedSize(true);

        addressList.setLayoutManager(new LinearLayoutManager(this));
        addressList.setHasFixedSize(true);
        mDeliveryAddressAdapter = new DeliveryAddressAdapter(R.layout.item_delivery_address, null, DeliveryAddrssActivity.this);
        addressList.setAdapter(mDeliveryAddressAdapter);

        add_address_tv.setOnClickListener(this);
        title_back.setOnClickListener(this);
    }

    protected void initView() {

    }

    @Override
    protected void Request() {
        Submit();
    }

    public void Submit() {
        final Gson gson = new Gson();
        mMrAddressData = new MrAddressData();
        OkGo.post(RequesURL.SHIPPINGADDRESSMANAGEMENT)
                .tag(this)
                .params("uid", MyApplication.getInstance().getUserid())
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

                        List<SelectedBean> datas = new ArrayList<>();
                        final AddressList addressList = gson.fromJson(s, AddressList.class);
                        for (int i = 0; i < addressList.getData().size(); i++) {
                            datas.add(new SelectedBean(addressList.getData().get(i), addressList.getData().get(i).getIsstate().equals("1") ? true : false));
                        }
                        mDeliveryAddressAdapter.setNewData(datas);
                        if (mDeliveryAddressAdapter.getData().size() == 0) {
                            mProgressActivity.showEmpty(getResources().getDrawable(R.mipmap.ic_empty_page), "", "咦...没有任何内容，赶快增加地址吧!", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(DeliveryAddrssActivity.this, EditShippingAddressActivity.class);
                                    startActivityForResult(intent, 1);
                                }
                            });
                        }
                    }


                    @Override
                    public void onSuccess(String s, Call call, Response response) {



                    }
                });

        OkGo.post(RequesURL.MRADDRESS)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .params("uid", MyApplication.getInstance().getUserid())
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Logger.json(s);
                        MrAddress mrAddress = gson.fromJson(s, MrAddress.class);
                        mMrAddressData = mrAddress.getData();
                    }
                });

    }

    @Override
    protected Context getActivityContext() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address_tv:
                Intent intent = new Intent(this, EditShippingAddressActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.title_back:
                if(mDeliveryAddressAdapter.getItemCount()==0){
                    mMrAddressData = new MrAddressData();
                }
                Intent intenta =new Intent();
                intenta.putExtra("address", mMrAddressData);
                setResult(RESULT_OK, intenta);
                MyApplication.getInstance().finishActivity(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(mDeliveryAddressAdapter.getItemCount()==0){
            mMrAddressData = new MrAddressData();
        }
        Intent intenta =new Intent();
        intenta.putExtra("address", mMrAddressData);
        setResult(RESULT_OK, intenta);
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Submit();
        }
    }


}
