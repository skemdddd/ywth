package com.example.dddkj.ypth.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dddkj.ypth.Adapter.OrderGoodsAdapter;
import com.example.dddkj.ypth.Base.BaseActivity;
import com.example.dddkj.ypth.Entity.DataZprice;
import com.example.dddkj.ypth.Entity.GroupInfo;
import com.example.dddkj.ypth.Entity.MrAddressData;
import com.example.dddkj.ypth.Entity.ProductInfo;
import com.example.dddkj.ypth.Entity.SerializableHashMap;
import com.example.dddkj.ypth.Interfaces.OrderInterface;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;
import com.example.dddkj.ypth.Widget.Titlebar;
import com.example.dddkj.ypth.common.RequesURL;
import com.example.dddkj.ypth.utils.T;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.dddkj.ypth.R.id.totalPrice;

/**
 * 项目名称：亿我同行
 * <p>
 * 创建时间：2017/4/24 12:50
 */

public class OrderGoods extends BaseActivity implements OrderInterface {
    @BindView(R.id.useName_tv)
    TextView useName_tv;
    @BindView(R.id.utel_tv)
    TextView utel_tv;
    @BindView(R.id.address_tv)
    TextView address_tv;
    @BindView(R.id.orderList_RV)
    RecyclerView mRecyclerView;
    @BindView(totalPrice)
    TextView totalPrice_tv;
    @BindView(R.id.title)
    Titlebar title;
    @BindView(R.id.confirm_order_btn)
    Button confirm_order_btn;
    @BindView(R.id.addressAdmin_tv)
    TextView addressAdmin_tv;
    @BindView(R.id.edit_address_rl)
    RelativeLayout edit_address_rl;
    @BindView(R.id.site_tv)
    TextView site_tv;
    SerializableHashMap children;
    OrderGoodsAdapter mOrderGoodsAdapter;
    private Map<String, List<ProductInfo>> childrenTransmit = new HashMap<>();
    private List<GroupInfo> groupsransmit = new ArrayList<GroupInfo>();
    MrAddressData mrAddressData;
    private List<String> shopid = new ArrayList<>();
    private List<String> goodsid = new ArrayList<>();
    private List<String> zprice = new ArrayList<>();
    private List<String> yunfei = new ArrayList<>();
    private List<String> goodsNum = new ArrayList<>();
    private List<String> goodsPrice = new ArrayList<>();
    private List<String> content = new ArrayList<>();
    List<EditText> mEditText = new ArrayList<>();
    DataZprice dataZprice;
    Gson mGson;
    double totalPricea;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.acticity_ordergoods);
        mGson = new Gson();

    }

    public void initView() {
        Intent intent = getIntent();
        title.setVisibilityHide("im");
        title.setVisibilityHide("seek");
        title.setText("确认订单");
        children = (SerializableHashMap) intent.getSerializableExtra("list");
        groupsransmit = (List<GroupInfo>) intent.getSerializableExtra("order");
        mrAddressData = (MrAddressData) intent.getSerializableExtra("address");
        childrenTransmit = children.getMap();
        totalPricea = Double.parseDouble(intent.getStringExtra("totalPrice"));

//       店铺id
        List<String> shopid = new ArrayList<>();
        for (int i = 0; i < groupsransmit.size(); i++) {
            shopid.add(groupsransmit.get(i).getId());

        }
        this.shopid = shopid;

//        地址
        useName_tv.setText(mrAddressData.getUName());
        utel_tv.setText(mrAddressData.getUTel());
        address_tv.setText(mrAddressData.getCityAddress());
        Logger.i("id" + groupsransmit.size());
//      商品信息
        ShowHiden(utel_tv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mOrderGoodsAdapter = new OrderGoodsAdapter(R.layout.item_order_goods, null, children.getMap());
        mOrderGoodsAdapter.setOrderInterface(this);
        mRecyclerView.setAdapter(mOrderGoodsAdapter);
        mOrderGoodsAdapter.setNewData(groupsransmit);


    }

    public void ShowHiden(TextView textView) {
        if (textView.getText().equals("")) {
            addressAdmin_tv.setVisibility(View.VISIBLE);
            site_tv.setVisibility(View.GONE);
        } else {
            addressAdmin_tv.setVisibility(View.GONE);
            site_tv.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected void setListener() {
        initView();
        title.setOnTitleBarClickListener(new Titlebar.TitleBarClickListener() {
            @Override
            public void onim() {

            }

            @Override
            public void Onseek() {

            }

            @Override
            public void Onback() {
                MyApplication.getInstance().finishActivity(OrderGoods.this);
            }
        });
        confirm_order_btn.setOnClickListener(this);
        edit_address_rl.setOnClickListener(this);
    }

    @Override
    protected void Request() {

    }

    @Override
    protected Context getActivityContext() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_order_btn:
                dataZprice = new DataZprice();
                if(mrAddressData.getAdrId()==null){
                    T.showShort(this,"请增加收货地址");
                }else{
                    Submit();
                }

                break;
            case R.id.edit_address_rl:
                Intent intent = new Intent(this, DeliveryAddrssActivity.class);
                startActivityForResult(intent, 1);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            mrAddressData = (MrAddressData) data.getSerializableExtra("address");
            useName_tv.setText(mrAddressData.getUName());
            utel_tv.setText(mrAddressData.getUTel());
            address_tv.setText(mrAddressData.getCityAddress());
            ShowHiden(utel_tv);
        }


    }

    public void Submit() {
        for (int i = 0; i < mEditText.size(); i++) {
            content.add(mEditText.get(i).getText().toString());


        }
        dataZprice.setAdrId(mrAddressData.getAdrId());
        dataZprice.setGoodsid(goodsid);
        dataZprice.setGoodsNum(goodsNum);
        dataZprice.setShopid(shopid);
        dataZprice.setUid(MyApplication.getInstance().getUserid());
        dataZprice.setYunfei(yunfei);
        dataZprice.setZprice(zprice);
        dataZprice.setContent(content);
        dataZprice.setGoodsPrice(goodsPrice);
//        上传确认订单
        OkGo.post(RequesURL.ORDERADD)
                .tag(this)
//                .upJson(jsonObject.toString())
                .params("order", mGson.toJson(dataZprice))
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Logger.json(s);
                        Logger.i("json" + s);
                        Intent intent = new Intent(OrderGoods.this,PaymentType.class);
                        startActivity(intent);
                    }
                });
        content.clear();

    }

    @Override
    public void zprice(String zprice) {
        this.zprice.add(zprice);
    }

    @Override
    public void yunfei(String yunfei) {
        this.yunfei.add(yunfei);
        totalPricea += Double.parseDouble(yunfei);
        totalPrice_tv.setText("合计 : " + "￥" + totalPricea + "");
    }

    @Override
    public void goodsNum(String goodsNum) {
        this.goodsNum.add(goodsNum);
    }

    @Override
    public void goodsid(String goodsid) {
        this.goodsid.add(goodsid);
    }

    @Override
    public void goodsPrice(String goodsPrice) {
        this.goodsPrice.add(goodsPrice);

    }

    @Override
    public void content(EditText editText) {
        mEditText.add(editText);
    }

}
