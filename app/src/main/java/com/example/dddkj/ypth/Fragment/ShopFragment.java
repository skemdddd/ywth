package com.example.dddkj.ypth.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dddkj.ypth.Adapter.ShopcartExpandableListViewAdapter;
import com.example.dddkj.ypth.Base.BaseFragment;
import com.example.dddkj.ypth.Entity.GroupInfo;
import com.example.dddkj.ypth.Entity.MrAddress;
import com.example.dddkj.ypth.Entity.MrAddressData;
import com.example.dddkj.ypth.Entity.ProductInfo;
import com.example.dddkj.ypth.Entity.RootCart;
import com.example.dddkj.ypth.Entity.SerializableHashMap;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;
import com.example.dddkj.ypth.Widget.SuperExpandableListView;
import com.example.dddkj.ypth.common.RequesURL;
import com.example.dddkj.ypth.ui.EnterStoreActivity;
import com.example.dddkj.ypth.ui.OrderGoods;
import com.example.dddkj.ypth.utils.ProgressActivity;
import com.example.dddkj.ypth.utils.T;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 项目名称：亿我同行
 * <购物>
 * 创建时间：2017/2/8 13:31
 */

public class ShopFragment extends BaseFragment implements ShopcartExpandableListViewAdapter.CheckInterface, View.OnClickListener, ShopcartExpandableListViewAdapter.ModifyCountInterface {
    @BindView(R.id.exListView)
    SuperExpandableListView exListView;
    @BindView(R.id.all_chekbox)
    CheckBox cb_check_all;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_go_to_pay)
    TextView tv_go_to_pay;
    @BindView(R.id.ProgressActivity)
    ProgressActivity mProgressActivity;
    View mView;
    private Context context;
    String id;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    List<ProductInfo> orderModel;

    private ShopcartExpandableListViewAdapter selva;


    private List<GroupInfo> groups = new ArrayList<GroupInfo>();// 组元素数据列表
    private Map<String, List<ProductInfo>> children = new HashMap<>();// 子元素数据列表

    private List<GroupInfo> groupsransmit = new ArrayList<GroupInfo>();// 组元素数据列表
    private Map<String, List<ProductInfo>> childrenTransmit = new HashMap<>();// 子元素数据列表

    private MrAddressData mMrAddressData = new MrAddressData();
    DecimalFormat df;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        context = getActivity();
        mView = inflater.inflate(R.layout.fragment_shop, container, false);
        df = new DecimalFormat("##0.00");
        return mView;

    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            virtualData();
        }
    }

    @Override
    public View getScrollableView() {
        return null;
    }


    private void initEvents() {
        selva = new ShopcartExpandableListViewAdapter(groups, children, getActivity());
        selva.setCheckInterface(this);// 关键步骤1,设置复选框接口
        selva.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        exListView.setAdapter(selva);

        for (int i = 0; i < selva.getGroupCount(); i++) {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现

        }

        cb_check_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_go_to_pay.setOnClickListener(this);

    }

    /**
     * 更新数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
     * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
     */
    public void virtualData() {

        final Gson gson = new Gson();
        cb_check_all.setChecked(false);
        tv_go_to_pay.setText("结算(0)");
        tv_total_price.setText("￥0.00");
        OkGo.post(RequesURL.CARTLIST)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .params("uid", MyApplication.getInstance().getUserid())
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(String s, Exception e) {
                        super.onAfter(s, e);
                        mProgressActivity.showContent();
                        children.clear();
                        groups.clear();
                        final RootCart root = gson.fromJson(s, RootCart.class);
                        if (root != null) {
                            for (int i = 0; i < root.getData().size(); i++) {

                                groups.add(new GroupInfo(root.getData().get(i).getShopId(), root.getData().get(i).getShopName(), root.getData().get(i).getDeliveryFreeMoney(),root.getData().get(i).getDeliveryMoney()));
                                List<ProductInfo> products = new ArrayList<>();
                                for (int j = 0; j < root.getData().get(i).getList().size(); j++) {
                                    products.add(new ProductInfo(root.getData().get(i).getList().get(j).getGoodsId(), "商品", root.getData().get(i).getList().get(j).getGoodsImg(), root.getData().get(i).getList().get(j).getGoodsVal(), root.getData().get(i).getList().get(j).getMarketPrice(), root.getData().get(i).getList().get(j).getGoodsName(),
                                            Double.parseDouble(root.getData().get(i).getList().get(j).getShopPrice()), Integer.parseInt(root.getData().get(i).getList().get(j).getGoodsCnt()), root.getData().get(i).getList().get(j).getStock()));

                                }
                                children.put(groups.get(i).getId(), products);
                            }
                        }
                        initEvents();
                        exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                                Intent intent = new Intent(getActivity(), EnterStoreActivity.class);
                                intent.putExtra("shopid", root.getData().get(i).getShopId());
                                startActivity(intent);
                                return true;
                            }
                        });

                        if (root.getData().size() == 0) {
                            mProgressActivity.showEmpty(getResources().getDrawable(R.mipmap.sdsdds), "", "咦...购物车是空的，快去选择心爱的商品吧", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        mProgressActivity.showLoading();


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
                        MrAddress mrAddress = gson.fromJson(s, MrAddress.class);
                        mMrAddressData = mrAddress.getData();
                    }
                });

    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            cb_check_all.setChecked(true);
        else
            cb_check_all.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck())
            cb_check_all.setChecked(true);
        else
            cb_check_all.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked, String stock) {
        ProductInfo product = (ProductInfo) selva.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        if (currentCount >= Integer.valueOf(stock)) {
            T.showShort(getActivity(), "超出库存量" + stock);
            return;
        }
        currentCount++;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ProductInfo product = (ProductInfo) selva.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        if (currentCount == 1)
            return;
        currentCount--;

        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");

        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void unChekbox(boolean cancel) {

    }

    @Override
    public void onClick(View v) {
        AlertDialog alert;
        switch (v.getId()) {
            case R.id.all_chekbox:
                doCheckAll();
                break;
            case R.id.tv_go_to_pay:
                if (totalCount == 0) {
                    Toast.makeText(context, "请选择要支付的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(context).create();
                alert.setTitle("操作提示");
                alert.setMessage("总计:\n" + totalCount + "种商品\n" + df.format(totalPrice) + "元");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SerializableHashMap myMap = new SerializableHashMap();
                        Intent intent = new Intent(getActivity(), OrderGoods.class);
                        myMap.setMap(childrenTransmit);
                        intent.putExtra("list", myMap);
                        intent.putExtra("order", (Serializable) groupsransmit);
                        intent.putExtra("address", mMrAddressData);
                        intent.putExtra("totalPrice", df.format(totalPrice));
                        Logger.i("totalPrice" + tv_total_price.getText());
                        totalPrice = 0.00;
                        totalCount = 0;
                        startActivity(intent);
                        return;
                    }
                });
                alert.show();
                break;
            case R.id.tv_delete:
                if (totalCount == 0) {
                    Toast.makeText(context, "请选择要移除的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(context).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doDelete();
                    }
                });
                alert.show();
                break;
        }
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    public void doDelete() {
        List<GroupInfo> toBeDeleteGroups = new ArrayList<GroupInfo>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            if (group.isChoosed()) {

                toBeDeleteGroups.add(group);
            }
            List<ProductInfo> toBeDeleteProducts = new ArrayList<ProductInfo>();// 待删除的子元素列表
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    toBeDeleteProducts.add(childs.get(j));
                }
            }
            childs.removeAll(toBeDeleteProducts);

        }

        groups.removeAll(toBeDeleteGroups);

        selva.notifyDataSetChanged();
        calculate();
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(cb_check_all.isChecked());
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(cb_check_all.isChecked());
            }
        }
        selva.notifyDataSetChanged();
        calculate();
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        childrenTransmit.clear();
        groupsransmit.clear();
        for (int i = 0; i < groups.size(); i++) {
            orderModel = new ArrayList<>();
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                ProductInfo product = childs.get(j);
                if (product.isChoosed()) {
                    id = groups.get(i).getId();
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                    orderModel.add(product);
                }

            }

            if (orderModel.size() != 0) {
                childrenTransmit.put(id, orderModel);
                if (id.equals(groups.get(i).getId())) {
                    groupsransmit.add(group);
                }
            }


        }

        tv_total_price.setText("￥" + df.format(totalPrice)+"");
        tv_go_to_pay.setText("去支付(" + totalCount + ")");
    }

    private boolean isAllCheck() {
        for (GroupInfo group : groups) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }
}
