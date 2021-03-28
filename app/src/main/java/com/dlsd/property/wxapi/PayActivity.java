package com.dlsd.property.wxapi;

import android.content.Intent;
import android.view.View;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityPayBinding;
import com.dlsd.property.db.Order;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.view.View.VISIBLE;

public class PayActivity extends BaseActivity implements View.OnClickListener {

    private ActivityPayBinding binding;
    private String mOrderId;
    private String mTotleFee;
    //0 支付宝  1 微信
    private int payWay = 0;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay);
    }

    @Override
    protected void initView() {
        binding.llyAliPay.setOnClickListener(this);
        binding.llyWxPay.setOnClickListener(this);
        binding.btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mOrderId = getIntent().getStringExtra("orderId");
        mTotleFee = getIntent().getStringExtra("totleFee");
        binding.tvNum.setText(mTotleFee);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_ali_pay:
                payWay = 0;
                binding.ivAli.setVisibility(VISIBLE);
                binding.ivWx.setVisibility(View.GONE);
                break;
            case R.id.lly_wx_pay:
                payWay = 1;
                binding.ivAli.setVisibility(View.GONE);
                binding.ivWx.setVisibility(VISIBLE);
                break;
            case R.id.btn_confirm:
                BmobQuery<Order> query = new BmobQuery<Order>();
                query.addWhereEqualTo("objectId", mOrderId);
                query.findObjects(new FindListener<Order>() {
                    @Override
                    public void done(List<Order> object, BmobException e) {
                        if (e == null) {
                            if (object.size() > 0) {
                                Order order = object.get(0);
                                order.setPayWay(payWay);
                                order.setState(1);
                                order.setPayTime(System.currentTimeMillis());
                                order.setTotleFee(Float.parseFloat(binding.tvNum.getText().toString().trim()));
                                order.update(mOrderId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (null == e) {
                                            startActivity(new Intent(PayActivity.this, PayResultActivity.class));
                                            finish();
                                        } else {
                                            showToast(R.string.error_msg);
                                        }
                                    }
                                });
                            }
                        } else {
                            showToast(getString(R.string.error_msg));
                        }
                    }
                });
                break;
        }
    }
}
