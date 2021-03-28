package com.dlsd.property.activitys.base;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityRegisterNextBinding;
import com.dlsd.property.db.User;
import com.dlsd.property.models.RegisterModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import zhangphil.iosdialog.widget.AlertDialog;


/**
 * 手机号
 */
public class RegisterNextActivity extends BaseActivity implements View.OnClickListener {

    private ActivityRegisterNextBinding binding;
    private RegisterModel registerModel;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_next);
    }

    @Override
    protected void initView() {
        binding.btnConfirm.setOnClickListener(this);
        binding.llyLogin.setOnClickListener(this);
        binding.llyNotice.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        registerModel = new RegisterModel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                String phone = binding.etPhone.getText().toString().trim();
                if (phone.length() == 11 && phone.substring(0, 1).equals("1")) {
                    registerModel.setPhone(phone);
                } else {
                    showToast("手机号不合法,请认真核对");
                    return;
                }
                checkPhone(phone);
                break;
            case R.id.lly_login:
                startActivity(new Intent(RegisterNextActivity.this, LoginActivity.class));
                break;
            case R.id.lly_notice:
                showRegisterAgreementDialog();
                break;
        }
    }

    private void showRegisterAgreementDialog() {
        new AlertDialog(RegisterNextActivity.this).builder()
                .setTitle("平台协议")
                .setMsg(getString(R.string.user_notice))
                .setMsgTextSize(12)
                .setMsgGravuty(Gravity.LEFT)
                .setPositiveButton("确认", "#5E76EF", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    /**
     * 校验手机号是否注册
     */
    private void checkPhone(String phone) {
        BmobQuery<User> categoryBmobQuery = new BmobQuery<User>();
        categoryBmobQuery.addWhereEqualTo("phone", phone);
        categoryBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        showToast("手机号已注册，请直接登录");
                    } else {
                        Intent intent = new Intent(RegisterNextActivity.this, RegisterSetPwdActivity.class);
                        intent.putExtra("model", registerModel);
                        startActivity(intent);
                    }
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }
}
