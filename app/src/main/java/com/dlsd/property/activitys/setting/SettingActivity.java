package com.dlsd.property.activitys.setting;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.activitys.base.LoginActivity;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivitySettingBinding;
import com.dlsd.property.utils.PackageUtil;
import com.dlsd.property.utils.SPUtil;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySettingBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
    }

    @Override
    protected void initView() {
        binding.llyExit.setOnClickListener(this);
        binding.tvVersion.setText("当前版本："+ PackageUtil.getVersionName(SettingActivity.this));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_exit:
                SPUtil.clear(SettingActivity.this);
                MyApp.mUserLogin = null;
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
