package com.example.dddkj.ypth;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dddkj.ypth.Base.BaseActivity;
import com.example.dddkj.ypth.Fragment.FragmentController;
import com.example.dddkj.ypth.Fragment.ShopFragment;
import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.common.Constant;
import com.example.dddkj.ypth.ui.LoginAcivity;
import com.example.dddkj.ypth.utils.LoginState;
import com.example.dddkj.ypth.utils.SPUtils;

import butterknife.BindView;

import static com.example.dddkj.ypth.R.id.classifyTab_rdoBtn;
import static com.example.dddkj.ypth.R.id.homeTab_rdoBtn;

/**
 * 项目名称：亿我同行
 * <主页>
 * 创建时间：2017/2/8 10:49
 */

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.hometab_radio)
    RadioGroup mHomeTab;
    @BindView(R.id.shopTab_rdoBtn)
    RadioButton shopTab_rdoBtn;
    public FragmentController controller;
    int type=0;
    String userid;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
        controller = FragmentController.getInstance(this, R.id.hometab_context);
        controller.showFragment(0);
//        获取uid
        userid= (String) SPUtils.get(MainActivity.this,"userid","", Constant.FILE_NAME);
        MyApplication.getInstance().setUserid(userid);



    }


    @Override
    protected void setListener() {
        mHomeTab.setOnCheckedChangeListener(this);



    }


    @Override
    protected void Request() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }

    @Override
    protected Context getActivityContext() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        ShopFragment shopFragment= (ShopFragment) controller.getFragment(2);
        shopFragment.virtualData();
        if(Constant.isShopping()){
            shopTab_rdoBtn.setChecked(true);
            Constant.setShopping(false);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                userid= (String) SPUtils.get(MainActivity.this,"userid","", Constant.FILE_NAME);
                MyApplication.getInstance().setUserid(userid);
                mHomeTab.check(mHomeTab.getChildAt(type).getId());
                break;
            case 1:
                mHomeTab.check(mHomeTab.getChildAt(0).getId());
                break;


        }
    }



    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case homeTab_rdoBtn:
                controller.showFragment(0);
                type=0;
                break;
            case classifyTab_rdoBtn:
                controller.showFragment(1);
                type=1;
                break;
            case R.id.shopTab_rdoBtn:
                if(LoginState.LoginState()){
                    Intent intent = new Intent(this, LoginAcivity.class);
                    startActivityForResult(intent,0);
                    radioGroup.clearCheck();
                }else{
                    controller.showFragment(2);
                }

            break;
            case R.id.myTab_rdoBtn:
                if(LoginState.LoginState()){
                    Intent intent = new Intent(this, LoginAcivity.class);
                    startActivityForResult(intent,0);
                    radioGroup.clearCheck();
                }else{
                    controller.showFragment(3);
                }

        }


    }

}
