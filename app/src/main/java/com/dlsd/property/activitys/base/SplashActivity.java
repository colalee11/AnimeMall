package com.dlsd.property.activitys.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dlsd.property.R;
import com.dlsd.property.activitys.MainActivity;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.guard.activitys.GuardMainActivity;
import com.dlsd.property.utils.AppLoginUtil;
import com.dlsd.property.utils.SPUtil;


public class SplashActivity extends BaseActivity {

   // private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initBinding() {
      //  binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppLoginUtil.hasLogin()) {
                    toHome();
                } else {
                    toLogin();
                }
                finish();
            }
        }, 2000);
    }

    @Override
    protected void initData() {

    }

    private void toLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }


    private void toHome() {
        int role = SPUtil.get(SplashActivity.this, SPUtil.ROLE, 1);
        if(role>0){
            //用户
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else {
            //管理员
            startActivity(new Intent(SplashActivity.this, GuardMainActivity.class));
        }
    }
}
