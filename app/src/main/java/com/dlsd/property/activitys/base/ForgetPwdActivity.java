package com.dlsd.property.activitys.base;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.activitys.MainActivity;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.constant.RespResult;
import com.dlsd.property.constant.UrlConfig;
import com.dlsd.property.databinding.ActivityForgetPwdBinding;
import com.dlsd.property.db.User;
import com.dlsd.property.models.response.SendCodeResp;
import com.dlsd.property.models.response.VerifyCodeResp;
import com.dlsd.property.network.LoadCallBack;
import com.dlsd.property.network.OkHttpManager;
import com.dlsd.property.service.PhoneCode;
import com.dlsd.property.utils.CountDownTimer1Util;
import com.dlsd.property.utils.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Response;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {

    private ActivityForgetPwdBinding binding;
    private String codeSessionId;
    private PhoneCode mPhoneCode;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_pwd);
    }

    @Override
    protected void initView() {
        binding.llyRegistered.setOnClickListener(this);
        binding.btnConfirm.setOnClickListener(this);
        binding.tvSendVcode.setOnClickListener(this);
        binding.tvToLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fixedPhone("10655");
    }

    /**
     * 固定手机号码
     */
    private void fixedPhone(String phone) {
        mPhoneCode = new PhoneCode(this, new Handler(), phone,
                new PhoneCode.SmsListener() {
                    @Override
                    public void onResult(String result) {
                        showToast(result);
                        binding.etVcode.setText(result);
                    }
                });
        // 注册短信变化监听
        this.getContentResolver().registerContentObserver(
                Uri.parse("content://sms/"), true, mPhoneCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_registered:
                startActivity(new Intent(ForgetPwdActivity.this, RegisterNextActivity.class));
                finish();
                break;
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(binding.etVcode.getText().toString().trim())) {
                    showToast("请输入验证码");
                    return;
                }
                verifyCode();
                break;
            case R.id.tv_send_vcode:
                String phone = binding.etPhone.getText().toString().trim();
                if (phone.length() == 11 && phone.substring(0, 1).equals("1")) {
                    sendCode(phone);
                } else {
                    showToast("手机号不合法,请认真核对");
                    return;
                }
                break;
            case R.id.tv_to_login:
                onBackPressed();
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode(String phone) {
        // 访问网络的方法
        checkPhoneRegister(phone);
    }

    /**
     * 校验手机号是否注册
     */
    private void checkPhoneRegister(String phone) {
        // 访问网络的方法
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("phone", phone);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        MyApp.mUserLogin = object.get(0);
                        showToast("验证码已发送，请注意查收。");
                    } else {
                        showToast("该手机号暂未注册");
                    }
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }


    /**
     * 校验验证码
     */
    private void verifyCode() {
        // 访问网络的方法
        Intent intent = new Intent(ForgetPwdActivity.this, ResetPwdActivity.class);
        intent.putExtra("phone", binding.etPhone.getText().toString().trim());
        startActivity(intent);
    }
}
