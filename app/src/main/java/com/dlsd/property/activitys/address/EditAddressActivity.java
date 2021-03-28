package com.dlsd.property.activitys.address;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityEditAddressBinding;
import com.dlsd.property.db.Address;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EditAddressActivity extends BaseActivity implements View.OnClickListener {

    private ActivityEditAddressBinding binding;
    private Address mAddress;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_address);
    }

    @Override
    protected void initView() {

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

        mAddress = (Address) getIntent().getSerializableExtra("model");
        binding.btnConfirm.setOnClickListener(this);

        binding.etName.setText(mAddress.getName());
        binding.etPhone.setText(mAddress.getPhone());
        binding.etAddressSub.setText(mAddress.getAddress());
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (verification()) {
                    getAddressEdit();
                }
                break;
        }
    }

    private boolean verification() {
        boolean result = true;
        if (TextUtils.isEmpty(binding.etName.getText().toString().trim())) {
            showToast("请输入患者姓名");
            return false;
        }
        if (TextUtils.isEmpty(binding.etPhone.getText().toString().trim()) || binding.etPhone.getText().toString().trim().length()!=11) {
            showToast("请输入11位手机号码");
            return false;
        }
        if (TextUtils.isEmpty(binding.etAddressSub.getText().toString().trim())) {
            showToast("请输入详细地址");
            return false;
        }
        return result;
    }


    private void getAddressEdit() {
        mAddress.setName(binding.etName.getText().toString().trim());
        mAddress.setPhone(binding.etPhone.getText().toString().trim());
        mAddress.setAddress(binding.etAddressSub.getText().toString().trim());
        mAddress.setIsdefault(false);
        mAddress.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("修改成功");
                    finish();
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }
}
