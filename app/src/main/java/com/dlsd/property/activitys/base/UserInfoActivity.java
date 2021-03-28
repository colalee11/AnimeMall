package com.dlsd.property.activitys.base;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityUserInfoBinding;
import com.dlsd.property.db.User;
import com.dlsd.property.utils.SPUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UserInfoActivity extends BaseActivity {

    private ActivityUserInfoBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
    }

    @Override
    protected void initView() {
        binding.footerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUserInfo();
            }
        });
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(mUser.getRealName()) && !mUser.getRealName().equals("null")) {
            binding.etRealName.setText(mUser.getRealName());
        }
        if (!TextUtils.isEmpty(mUser.getNickName()) && !mUser.getNickName().equals("null")) {
            binding.etNickName.setText(mUser.getNickName());
        }
        if (!TextUtils.isEmpty(mUser.getSex()) && !mUser.getSex().equals("null")) {
            binding.etSex.setText(mUser.getSex());
        }
        if (!TextUtils.isEmpty(mUser.getMood()) && !mUser.getMood().equals("null")) {
            binding.etMood.setText(mUser.getMood());
        }
    }

    private void UpdateUserInfo() {
        showLoading();
        //更新User表里面id为6b6c11c537的数据
        User p2 = new User();
        p2 = mUser;
        if (!TextUtils.isEmpty(binding.etRealName.getText().toString())) {
            p2.setRealName(binding.etRealName.getText().toString());
        }
        if (!TextUtils.isEmpty(binding.etNickName.getText().toString())) {
            p2.setNickName(binding.etNickName.getText().toString());
        }
        if (!TextUtils.isEmpty(binding.etSex.getText().toString())) {
            p2.setSex(binding.etSex.getText().toString());
        }
        if (!TextUtils.isEmpty(binding.etMood.getText().toString())) {
            p2.setMood(binding.etMood.getText().toString());
        }
        User finalP = p2;
        p2.update(mUser.getObjectId(), new UpdateListener() {

            @Override
            public void done(BmobException e) {
                hideLoading();
                if (e == null) {
                    MyApp.mUserLogin = finalP;
                    SPUtil.put(UserInfoActivity.this, SPUtil.NICK_NAME, MyApp.mUserLogin.getNickName() + "");
                    SPUtil.put(UserInfoActivity.this, SPUtil.USER_NAME, MyApp.mUserLogin.getRealName() + "");
                    SPUtil.put(UserInfoActivity.this, SPUtil.MOOD, MyApp.mUserLogin.getMood() + "");
                    SPUtil.put(UserInfoActivity.this, SPUtil.SEX, MyApp.mUserLogin.getSex() + "");
                    showToast("保存成功");
                    finish();
                } else {
                    showToast("保存失败，请重试");
                }
            }
        });
    }
}
