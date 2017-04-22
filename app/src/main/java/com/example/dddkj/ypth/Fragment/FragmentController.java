package com.example.dddkj.ypth.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.dddkj.ypth.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * 项目名称：亿我同行
 * <Fragment 管理>
 * 创建时间：2017/2/8 13:42
 */

public class FragmentController {
    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FragmentController controller;

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }


    public static void onDestroy() {
        controller = null;
    }

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new ClassifyFragment());
        fragments.add(new ShopFragment());
        fragments.add(new MyFragment());
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
//		ft.commit();
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        Logger.i("456"+fragments.size());
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
//		ft.commit();
        ft.commitAllowingStateLoss();
    }
    public void newFragment(int position){
        fragments.remove(position);
        fragments.add(position,new ShopFragment());
        Logger.i("123"+fragments.size());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.hometab_context, fragments.get(2));
        ft.hide(fragments.get(2));
//		ft.commit();
        ft.commitAllowingStateLoss();

    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
//		ft.commit();
        ft.commitAllowingStateLoss();
    }


    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}
