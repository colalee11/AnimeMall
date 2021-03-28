package com.dlsd.property.activitys.base;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.constant.RespResult;
import com.dlsd.property.constant.UrlConfig;
import com.dlsd.property.databinding.ActivityResetPwdBinding;
import com.dlsd.property.db.User;
import com.dlsd.property.models.response.ResetPasswordResp;
import com.dlsd.property.network.LoadCallBack;
import com.dlsd.property.network.OkHttpManager;
import com.dlsd.property.utils.PwdCheckUtil;
import com.dlsd.property.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Response;

public class ResetPwdActivity extends BaseActivity implements View.OnClickListener {

    private ActivityResetPwdBinding binding;
    private String phone;
    private boolean seePwd = false;
    private boolean seeConfirmPwd = false;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pwd);
    }

    @Override
    protected void initView() {
        binding.ivPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seePwd = !seePwd;
                if (seePwd) {
                    setPasswordVisibility(binding.etPwd, true);
                    binding.ivPwd.setImageResource(R.drawable.icon_eye_open);
                } else {
                    setPasswordVisibility(binding.etPwd, false);
                    binding.ivPwd.setImageResource(R.drawable.icon_eye_close);
                }
            }
        });

        binding.ivConfirmPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeConfirmPwd = !seeConfirmPwd;
                if (seeConfirmPwd) {
                    setPasswordVisibility(binding.etConfirmPwd, true);
                    binding.ivConfirmPwd.setImageResource(R.drawable.icon_eye_open);
                } else {
                    setPasswordVisibility(binding.etConfirmPwd, false);
                    binding.ivConfirmPwd.setImageResource(R.drawable.icon_eye_close);
                }
            }
        });
        binding.btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                String pwd = binding.etPwd.getText().toString().trim();
                String confirmPwd = binding.etConfirmPwd.getText().toString().trim();
                if (pwd.length() < 8) {
                    showToast("请设置不少于8位密码");
                    return;
                } else if (!PwdCheckUtil.isContainAll(pwd)) {
                    showToast("必须同时包含大小写字母及数字");
                    return;
                } else if (!pwd.equals(confirmPwd)) {
                    showToast("确认密码与密码不一致,请认真核对");
                    return;
                }
                resetPassword(pwd);
                break;
        }
    }

    /**
     * @category 让EditText内的密码显示明文或显示mask符号
     */
    public void setPasswordVisibility(EditText et, boolean flag) {
        int type = flag ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et.setInputType(type);
    }

    /**
     * 修改密码
     */
    private void resetPassword(String pwd) {
        // 访问网络的方法
        User p2 = new User();
        p2 = mUser;
        p2.setPwd(pwd);
        User finalP = p2;
        p2.update(mUser.getObjectId(), new UpdateListener() {

            @Override
            public void done(BmobException e) {
                hideLoading();
                if (e == null) {
                    MyApp.mUserLogin = finalP;
                    showToast("修改成功");
                    startActivity(new Intent(ResetPwdActivity.this, LoginActivity.class));
                    finish();
                } else {
                    showToast("保存失败，请重试");
                }
            }
        });
    }
}
