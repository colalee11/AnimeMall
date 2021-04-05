package com.dlsd.property;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.dlsd.property.constant.Constants;
import com.dlsd.property.db.User;
import com.dlsd.property.utils.AppLoginUtil;

import cn.bmob.v3.Bmob;


public class MyApp extends MultiDexApplication {

    private static Context context;
    private volatile static MyApp instance;
    public static User mUserLogin = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MyApp.context = getApplicationContext();
        //初始化Bmob
        Bmob.initialize(this, Constants.BMOB_ID);
        //设置推送
        if (AppLoginUtil.hasLogin()) {
            mUserLogin = AppLoginUtil.createLoginModel();
        } else {
            mUserLogin = null;
        }
        //CrashReport.initCrashReport(getApplicationContext(), "5a7495b95c", false);
        //CrashHandler.getInstance().init(this);
    }

    public static Context getAppContext() {
        return MyApp.context;
    }

    public static MyApp getInstance() {
        if (instance == null) {
            synchronized (MyApp.class) {
                if (instance == null) {
                    instance = new MyApp();
                }
            }
        }
        return instance;
    }
}
