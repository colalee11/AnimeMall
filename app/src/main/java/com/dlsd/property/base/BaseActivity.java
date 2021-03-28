package com.dlsd.property.base;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.db.User;
import com.dlsd.property.utils.StatusBarUtil;
import com.dlsd.property.utils.ToastUtil;

public abstract class BaseActivity extends FragmentActivity {

    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();
    //是否显示标题栏
    private boolean isShowTitle = true;
    //是否显示状态栏
    private boolean isShowStatusBar = true;
    //是否允许旋转屏幕
    private boolean isAllowScreenRoate = true;

    // 上下文
    protected Context mContext;
    // 传过来的Bundle数据
    protected Bundle mBundle;

    private Dialog dialog;

    public User mUser;

    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSys();
        mContext = BaseActivity.this;
        mBundle = getIntent().getExtras();
        setCustomDensity(this, this.getApplication());
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (!isShowStatusBar) {
            getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
        //初始化DataBinding，绑定View
        initBinding();

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }

        //设置屏幕是否可旋转
        if (android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            if (!isAllowScreenRoate) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_app_loading);

        mUser = MyApp.mUserLogin;

        //初始化
        initView();
        //设置数据
        initData();
    }

    // 在setContentView前的初始化
    protected void initSys() {

    }
    public void showdialog(){
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("正在登录中,请稍后..")
                .create();
        alertDialog.show();
    }
    public void dismissdialog(){
        alertDialog.dismiss();
    }
    /**
     * 初始化DataBinding，绑定View
     *
     * @return 布局id
     */
    protected abstract void initBinding();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置数据
     */
    protected abstract void initData();

    /**
     * 设置是否显示标题栏
     *
     * @param showTitle true or false
     */
    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    /**
     * 设置是否显示状态栏
     *
     * @param showStatusBar true or false
     */
    public void setShowStatusBar(boolean showStatusBar) {
        isShowStatusBar = showStatusBar;
    }

    /**
     * 是否允许屏幕旋转
     *
     * @param allowScreenRoate true or false
     */
    public void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮之间的间隔，目前为1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View view) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(view);
            }
        }
    }

    // Toast出Msg
    protected void showToast(String msg) {
        ToastUtil.showShort(this, msg);
    }

    // Toast出资源
    protected void showToast(int msg) {
        ToastUtil.showShort(this, msg);
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    public abstract class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (view != null && null != imm) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInput(view, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    private static float sNoncompatDensity;
    private static float sNoncompatScaleDensity;

    public static void setCustomDensity(@Nullable Activity activity, @Nullable Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaledDensity = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public void showLoading() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void hideLoading() {
        dialog.dismiss();
    }
}
