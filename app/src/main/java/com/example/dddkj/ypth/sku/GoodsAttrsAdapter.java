package com.example.dddkj.ypth.sku;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dddkj.ypth.Entity.AttrVal;
import com.example.dddkj.ypth.Entity.Attributes;
import com.example.dddkj.ypth.Entity.GoodsInfo;
import com.example.dddkj.ypth.Entity.StockGoods;
import com.example.dddkj.ypth.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by on 2017/1/16.
 */

public class GoodsAttrsAdapter extends BaseRecyclerAdapter<Attributes> {

    private SKUInterface myInterface;

    private SimpleArrayMap<Integer, String> saveClick;
    private Map<Integer,Integer> map;

    private List<StockGoods> stockGoodsList;//商品数据集合
    private String[] selectedValue;   //选中的属性
    private TextView[][] childrenViews;    //二维 装所有属性
    private String[][] childrenid; //二维 装所有id
    private String[][] childrenpic; //二维 装所有id



    private final int SELECTED = 0x100;
    private final int CANCEL = 0x101;

    public GoodsAttrsAdapter(Context ctx, List<Attributes> list, List<StockGoods> stockGoodsList) {
        super(ctx, list);
        this.stockGoodsList = stockGoodsList;
        saveClick = new SimpleArrayMap<>();
        childrenid=new String[list.size()][0];
        childrenpic=new String[list.size()][0];
        childrenViews = new TextView[list.size()][0];
        map=new HashMap<>();
        selectedValue = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            selectedValue[i] = "";
        }
    }

    public void setSKUInterface(SKUInterface myInterface) {
        this.myInterface = myInterface;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.sku_item;
    }


    @Override
    public void bindData(RecyclerViewHolder holder, int position, Attributes item) {
        TextView tv_ItemName = holder.getTextView(R.id.tv_ItemName);
        SKUViewGroup vg_skuItem = (SKUViewGroup) holder.getView(R.id.vg_skuItem);
        tv_ItemName.setText(item.getAttrName());
        List<AttrVal> childrens = item.getAttrVal();
        int childrenSize = childrens.size();
        TextView[] textViews = new TextView[childrenSize];
        String [] dis=new String[childrenSize];
        String [] pic=new String[childrenSize];
        for (int i = 0; i < childrenSize; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 10, 10);
            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(25, 10, 25, 10);
            textView.setLayoutParams(params);
            textView.setText(childrens.get(i).getAttrVal());
            textView.setBackgroundResource(R.drawable.sku_selected);
            textViews[i] = textView;
            dis[i]=childrens.get(i).getId();
            pic[i]=childrens.get(i).getPicurl();
            vg_skuItem.addView(textViews[i]);
        }
        childrenViews[position] = textViews;
        childrenid[position]= dis;
        childrenpic[position]=pic;

        initOptions();
        canClickOptions();
        getSelected();
    }

    private int focusPositionG, focusPositionC;

    private class MyOnClickListener implements View.OnClickListener {
        //点击操作 选中SELECTED   取消CANCEL
        private int operation;

        private int positionG;

        private int positionC;

        public MyOnClickListener(int operation, int positionG, int positionC) {
            this.operation = operation;
            this.positionG = positionG;
            this.positionC = positionC;

        }

        @Override
        public void onClick(View v) {
            focusPositionG = positionG;
            focusPositionC = positionC;
            String value = childrenViews[positionG][positionC].getText().toString();

            switch (operation) {
                case SELECTED:
                    map.put(positionG,Integer.parseInt(childrenid[positionG][positionC]));
                    Map newMap=sortMap(map);
                    for(int i=0;i<stockGoodsList.size();i++){
                        Logger.i("123"+handleMapParamToString(newMap));
                        if(handleMapParamToString(newMap).equals(stockGoodsList.get(i).getGoodsID())){
                            myInterface.selectedAttribute(handleMapParamToString(newMap),stockGoodsList.get(i).getPrice(),stockGoodsList.get(i).getStock());
                        }
                    }
                    myInterface.selectedAttribute(childrenpic[positionG][positionC]);
                    saveClick.put(positionG, positionC + "");
                    selectedValue[positionG] = value;
                    myInterface.selectedAttribute(selectedValue);
                    break;
                case CANCEL:
                    saveClick.put(positionG, "");
                    map.remove(positionG);
                    myInterface.uncheckAttribute(false);
                    if(map.size()==0){
                        myInterface.uncheckAttribute(true);
                    }
                    v.setBackgroundResource(R.drawable.sku_selected);
                    ((TextView)v).setTextColor(ContextCompat.getColor(mContext, R.color.blacktext));
                    for (int l = 0; l < selectedValue.length; l++) {
                        if (selectedValue[l].equals(value)) {
                            selectedValue[l] = "";
                            break;
                        }
                    }
                    myInterface.uncheckAttribute(selectedValue);
                    break;
            }
            initOptions();
            canClickOptions();
            getSelected();
        }
    }


    /**
     * 初始化选项（不可点击，焦点消失）
     */
    private void initOptions() {
        for (int y = 0; y < childrenViews.length; y++) {
            for (int z = 0; z < childrenViews[y].length; z++) {//循环所有属性
                TextView textView = childrenViews[y][z];
                textView.setEnabled(false);
                textView.setFocusable(false);
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.gray));//变灰

            }
        }
    }

    /**
     * 找到符合条件的选项变为可选
     */
    private void canClickOptions() {
        for (int i = 0; i < childrenViews.length; i++) {
            for (int j = 0; j < stockGoodsList.size(); j++) {
                boolean filter = false;
                List<GoodsInfo> goodsInfo = stockGoodsList.get(j).getGoodsInfo();
                for (int k = 0; k < selectedValue.length; k++) {
                    if (i == k || TextUtils.isEmpty(selectedValue[k])) {
                        continue;
                    }
                    if (!selectedValue[k].equals(goodsInfo
                            .get(k).getTabValue())) {
                        filter = true;
                        break;
                    }

                }


                if (!filter) {
                    for (int n = 0; n < childrenViews[i].length; n++) {
                        TextView textView = childrenViews[i][n];//拿到所有属性TextView
                        String name = textView.getText().toString();
                        //拿到属性名称
                        if (goodsInfo.get(i).getTabValue().equals(name)) {
                            textView.setEnabled(true);//符合就变成可点击
                            textView.setFocusable(true); //设置可以获取焦点
                            //不要让焦点乱跑
                            if (focusPositionG == i && focusPositionC == n) {
                               textView.setBackgroundResource(R.drawable.sku_selected);
                                textView.setTextColor(ContextCompat.getColor(mContext, R.color.blacktext));
                            } else {
                                textView.setBackgroundResource(R.drawable.sku_selected);
                                textView.setTextColor(ContextCompat.getColor(mContext, R.color.blacktext));
                            }
                            textView.setOnClickListener(new MyOnClickListener(SELECTED, i, n) {
                            });
//                            textView.setOnFocusChangeListener(new MyOnFocusChangeListener(i, n) {
//                            });
//                        }
                        }
                    }
                }
            }
        }
    }

    /**
     * 找到已经选中的选项，让其变红
     */
    private void getSelected() {
        for (int i = 0; i < childrenViews.length; i++) {
            for (int j = 0; j < childrenViews[i].length; j++) {//拿到每行属性Item
                TextView textView = childrenViews[i][j];//拿到所有属性TextView
                String value = textView.getText().toString();

                for (int m = 0; m < selectedValue.length; m++) {
                    if (selectedValue[m].equals(value)) {
                        textView.setBackgroundResource(R.drawable.sku_initialization);
                        textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
//                        选中背景变红
                        textView.setOnClickListener(new MyOnClickListener(CANCEL, i, j) {

                        });
                    }
                }
            }
        }

    }
    public static Map sortMap(Map oldMap) {
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> arg0,
                               Map.Entry<String, Integer> arg1) {
                return arg0.getValue() - arg1.getValue();
            }
        });
        Map newMap = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }
    @NonNull
    public  <K, V> String handleMapParamToString(Map map) {

        Iterator<Map.Entry<K,V>> i = map.entrySet().iterator();

        StringBuilder sb = new StringBuilder();

        for(;;){
            Map.Entry<K,V> m = i.next();
            K key = m.getKey();
            V value= m.getValue();
            sb.append(value);
            if(!i.hasNext()||key.equals("0")){
                return sb.toString();
            }
            sb.append(';');
        }
    }

}
