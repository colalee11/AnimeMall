package com.dlsd.property.activitys.address;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityAddAddressBinding;
import com.dlsd.property.db.Address;
import com.dlsd.property.view.SwitchButton;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAddAddressBinding binding;
    private String isDefault = "1";

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);
    }

    @Override
    protected void initView() {
        binding.btnConfirm.setOnClickListener(this);
        binding.switchBtnDefault.setmOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    isDefault = "0";
                } else {
                    isDefault = "1";
                }
            }
        });

        binding.etAddressSub.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 100) {
                    binding.etAddressSub.setText(charSequence.toString().substring(0, 100));
                    showToast("您最多能输入100个字");
                }
                binding.tvCountAddressSub.setText(charSequence.length() + "/" + 100);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (verification()) {
                    getAddressAdd();
                }
                break;
        }
    }

    private boolean verification() {
        boolean result = true;
        if (TextUtils.isEmpty(binding.etName.getText().toString().trim())) {
            showToast("请输入收货人姓名");
            return false;
        }
        if (TextUtils.isEmpty(binding.etPhone.getText().toString().trim()) || binding.etPhone.getText().toString().trim().length() != 11) {
            showToast("请输入11位手机号码");
            return false;
        }
        if (TextUtils.isEmpty(binding.etAddressSub.getText().toString().trim())) {
            showToast("请输入详细地址");
            return false;
        }
        return result;
    }


    private void getAddressAdd() {
        Address address = new Address();
        address.setName(binding.etName.getText().toString().trim());
        address.setPhone(binding.etPhone.getText().toString().trim());
        address.setAddress(binding.etAddressSub.getText().toString().trim());
        address.setIsdefault(false);
        address.setUser(mUser);
        address.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    showToast("添加成功");
                    finish();
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }
}
