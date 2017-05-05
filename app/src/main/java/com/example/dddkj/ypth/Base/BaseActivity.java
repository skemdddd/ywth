package com.example.dddkj.ypth.Base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dddkj.ypth.MyApplication.MyApplication;
import com.example.dddkj.ypth.R;

import butterknife.ButterKnife;
/**
 * 基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
	protected Context mContext;
	private ConnectivityManager manager;
	FragmentManager fManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}
		mContext = getActivityContext();
		initView();
		ButterKnife.bind(this);
		initdata();
		MyApplication.getInstance().addActivity(this);

	}
	/**
	 * 初始activity方法
	 */
	private void initView() {
		loadViewLayout();
	}
	private void initdata(){
		Request();
		setListener();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		StatService.onPause(mContext);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		StatService.onResume(mContext);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyApplication.getInstance().finishActivity(this);
	}
	/**
	 * 加载页面layout
	 */
	protected abstract void loadViewLayout();


	/**
	 * 设置各种事件的监听器
	 */
	protected abstract void setListener();



	/**
	 * 网路请求
	 */
	protected abstract void Request();
	/**
	 * Activity.this
	 */
	protected abstract Context getActivityContext();

	/**
	 * 弹出Toast
	 * 
	 * @param text
	 */
	public void showToast(String text) {
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	/**
	 * 获取屏幕宽度(px)
	 * 
	 * @param
	 * @return
	 */
	public int getMobileWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		return width;
	}

	/**
	 *
	 *
	 * @param
	 * @return
	 */
	public int getMobileHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels;
		return height;
	}

	/**
	 * 获取状态栏高度
	 * @return
	 */
	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	/*
	返回版本api
	 */
	public boolean SdkApi(){
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.KITKAT){
			return true;
		} else{
			return false;
		}
	}
	public boolean checkNetworkState() {
		boolean flag = false;
		//得到网络连接信息
		manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		//去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		return flag;
	}
	protected View getRootView(Activity context)
	{
		return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);

	}
	public String getVersionName()
	{
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
			String version = packInfo.versionName;
			return version;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return "1.0";
	}

}
