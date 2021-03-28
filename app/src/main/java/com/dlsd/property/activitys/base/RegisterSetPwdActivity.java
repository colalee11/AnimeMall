package com.dlsd.property.activitys.base;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.activitys.MainActivity;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.constant.Constants;
import com.dlsd.property.constant.UrlConfig;
import com.dlsd.property.databinding.ActivityRegisterSetpwdBinding;
import com.dlsd.property.db.User;
import com.dlsd.property.models.RegisterModel;
import com.dlsd.property.models.response.LoginTokenResp;
import com.dlsd.property.network.LoadCallBack;
import com.dlsd.property.network.OkHttpManager;
import com.dlsd.property.utils.AppLoginUtil;
import com.dlsd.property.utils.PwdCheckUtil;
import com.dlsd.property.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterSetPwdActivity extends BaseActivity implements View.OnClickListener {

    private ActivityRegisterSetpwdBinding binding;
    private RegisterModel registerModel;
    private boolean seePwd = false;
    private boolean seeConfirmPwd = false;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_setpwd);
    }

    @Override
    protected void initView() {
        binding.btnConfirm.setOnClickListener(this);

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
    }

    @Override
    protected void initData() {
        registerModel = (RegisterModel) getIntent().getSerializableExtra("model");
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
                } else if (!pwd.equals(confirmPwd)) {
                    showToast("确认密码与密码不一致,请认真核对");
                    return;
                }
                registerModel.setPassword(pwd);
                userRegister();
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
     * 注册
     */
    private void userRegister() {
        // 访问网络的方法
        User user = new User();
        user.setPhone(registerModel.getPhone());
        user.setPwd(registerModel.getPassword());
        user.setRole(1);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    showToast("注册成功");
                    startActivity(new Intent(RegisterSetPwdActivity.this, LoginActivity.class));
                    finish();
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }
}
