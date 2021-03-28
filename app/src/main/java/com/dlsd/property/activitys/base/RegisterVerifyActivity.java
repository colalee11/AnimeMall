package com.dlsd.property.activitys.base;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.constant.RespResult;
import com.dlsd.property.constant.UrlConfig;
import com.dlsd.property.databinding.ActivityRegisterVerifyBinding;
import com.dlsd.property.models.RegisterModel;
import com.dlsd.property.models.response.SendCodeResp;
import com.dlsd.property.models.response.VerifyCodeResp;
import com.dlsd.property.network.LoadCallBack;
import com.dlsd.property.network.OkHttpManager;
import com.dlsd.property.utils.CountDownTimerUtil;
import com.github.gongw.VerifyCodeView;
import com.github.gongw.sms.SmsVerifyCodeFilter;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterVerifyActivity extends BaseActivity implements View.OnClickListener {

    private ActivityRegisterVerifyBinding binding;
    private RegisterModel registerModel;
    private String codeSessionId;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_verify);
    }

    @Override
    protected void initView() {
        binding.llyChangePhone.setOnClickListener(this);
        binding.llyResend.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        registerModel = (RegisterModel) getIntent().getSerializableExtra("model");
        binding.tvSendPhone.setText("请输入 " + registerModel.getPhone() + " 收到的验证码");
        sendCode(registerModel.getPhone());

        binding.vcvCode.setOnAllFilledListener(new VerifyCodeView.OnAllFilledListener() {
            @Override
            public void onAllFilled(String text) {
                verifyCode(text, codeSessionId);
            }
        });
        SmsVerifyCodeFilter filter = new SmsVerifyCodeFilter();
        filter.setSmsSenderStart("10655");
        filter.setSmsSenderContains("10655");
        filter.setSmsBodyStart("【】");
        filter.setSmsBodyContains("验证码是");
        filter.setVerifyCodeCount(binding.vcvCode.getVcTextCount());
        binding.vcvCode.startListen(filter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_resend:
                binding.llyWait.setVisibility(View.VISIBLE);
                binding.llyResend.setVisibility(View.GONE);
                sendCode(registerModel.getPhone());
                break;
            case R.id.lly_change_phone:
                onBackPressed();
                break;
        }
    }


    /**
     * 发送验证码
     */
    private void sendCode(String phone) {
        // 访问网络的方法
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", phone);
        params.put("templateId", "cPESIOvyQC_9WZLcRaMpWG");
        params.put("region", "86");
        params.put("role","2");
        OkHttpManager.getInstance().getRequest(RegisterVerifyActivity.this, UrlConfig.SEND_CODE, params, new LoadCallBack<SendCodeResp>(this) {
            @Override
            protected void onSuccess(Call call, Response response, SendCodeResp resp) {
                Log.e("lsh", "onSuccess = " + UrlConfig.SEND_CODE);
                if (resp.getCode() == RespResult.SUCCESS) {
                    codeSessionId = resp.getData().getSessionId();
                    showToast("验证码发送成功");
                    CountDownTimerUtil mCountDownTimerUtils = new CountDownTimerUtil(binding.tvWait, 60000, 1000);
                    mCountDownTimerUtils.setOnFinishedListener(new CountDownTimerUtil.OnFinishedListener() {
                        @Override
                        public void onFinish() {
                            binding.llyWait.setVisibility(View.GONE);
                            binding.llyResend.setVisibility(View.VISIBLE);
                        }
                    });
                    mCountDownTimerUtils.start();
                } else {
                    showToast("验证码发送失败");
                }
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                showToast("验证码发送失败,请稍后尝试");
                binding.llyWait.setVisibility(View.GONE);
                binding.llyResend.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * 校验验证码
     */
    private void verifyCode(String code, String sessionId) {
        // 访问网络的方法
        Map<String, String> params = new HashMap<String, String>();
        params.put("code", code);
        params.put("sessionId", sessionId);
        OkHttpManager.getInstance().getRequest(RegisterVerifyActivity.this, UrlConfig.VERIFY_CODE, params, new LoadCallBack<VerifyCodeResp>(this) {
            @Override
            protected void onSuccess(Call call, Response response, VerifyCodeResp resp) {
                Log.e("lsh", "onSuccess = " + UrlConfig.VERIFY_CODE);
                if (resp.getCode() == RespResult.SUCCESS) {
                    Intent intent = new Intent(RegisterVerifyActivity.this, RegisterSetPwdActivity.class);
                    intent.putExtra("model", registerModel);
                    startActivity(intent);
                } else {
                    showToast("" + resp.getMsg());
                }
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                showToast("验证码校验失败，请重新发送");
            }
        });
    }

}
