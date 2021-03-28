package com.dlsd.property.wxapi;

import android.content.Intent;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dlsd.property.R;
import com.dlsd.property.activitys.MainActivity;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityPayResultBinding;
import com.dlsd.property.databinding.ActivitySignPayResultBinding;

import static com.dlsd.property.constant.Constants.REGISTERED_RESULT;

/**
 * 支付结果
 */
public class PayResultActivity extends BaseActivity implements View.OnClickListener {

    private ActivityPayResultBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_result);
    }

    @Override
    protected void initView() {
        binding.btnHome.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                Intent intent = new Intent(PayResultActivity.this, MainActivity.class);
                intent.putExtra(REGISTERED_RESULT, 0);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PayResultActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
