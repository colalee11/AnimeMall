package com.dlsd.property.activitys.detail;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dlsd.property.R;
import com.dlsd.property.activitys.address.AddressListActivity;
import com.dlsd.property.adapters.GoodItemsConfirmAdapter;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityConfirmOrderDetailBinding;
import com.dlsd.property.db.Address;
import com.dlsd.property.db.BusGoods;
import com.dlsd.property.db.Order;
import com.dlsd.property.db.OrderGoods;
import com.dlsd.property.wxapi.PayActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class ConfirmOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_ADDRESS = 1001;
    private ActivityConfirmOrderDetailBinding binding;

    private ArrayList<OrderGoods> mOrderGoodsDatas = new ArrayList<OrderGoods>();
    private ArrayList<BusGoods> mBusGoodsDatas = new ArrayList<BusGoods>();
    private GoodItemsConfirmAdapter mGoodItemsConfirmAdapter;
    private Address mAddress;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_order_detail);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        binding.btnConfirm.setOnClickListener(this);
        binding.llyChooseAddress.setOnClickListener(this);
        binding.llyAddress.setOnClickListener(this);
        mGoodItemsConfirmAdapter = new GoodItemsConfirmAdapter(ConfirmOrderDetailActivity.this, mOrderGoodsDatas);
        binding.rcvGoodsList.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvGoodsList.setAdapter(mGoodItemsConfirmAdapter);
    }

    @Override
    protected void initData() {
        mOrderGoodsDatas.clear();
        mBusGoodsDatas.clear();
        mOrderGoodsDatas.addAll((ArrayList<OrderGoods>) getIntent().getSerializableExtra("orderGoods"));
        if(null != getIntent().getSerializableExtra("busGoods")){
            mBusGoodsDatas.addAll((ArrayList<BusGoods>) getIntent().getSerializableExtra("busGoods"));
        }
        mGoodItemsConfirmAdapter.notifyDataSetChanged();
        totleFee();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm://下单
                if (null != mAddress) {
                    showLoading();
                    for (BusGoods orderGoods : mBusGoodsDatas) {
                        deleteBusGoods(orderGoods);
                    }
                    Order order = new Order();
                    order.setUser(mUser);
                    order.setAddress(mAddress);
                    order.setGoodsList(mOrderGoodsDatas);
                    order.setState(0);
                    order.setTotleFee(Float.parseFloat(binding.tvTotlePay.getText().toString().trim()));
                    order.setCreateTime(System.currentTimeMillis());
                    order.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            hideLoading();
                            if (e == null) {
                                Intent intent = new Intent(ConfirmOrderDetailActivity.this, PayActivity.class);
                                intent.putExtra("orderId", s);
                                intent.putExtra("totleFee", binding.tvTotlePay.getText().toString().trim());
                                startActivity(intent);
                                finish();
                            } else {
                                showToast(getString(R.string.error_msg));
                            }
                        }
                    });
                } else {
                    showToast("请选择邮寄地址");
                }
                break;
            case R.id.lly_choose_address:
                Intent intent = new Intent(ConfirmOrderDetailActivity.this, AddressListActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADDRESS);
                break;
            case R.id.lly_address:
                Intent addressIntent = new Intent(ConfirmOrderDetailActivity.this, AddressListActivity.class);
                startActivityForResult(addressIntent, REQUEST_CODE_ADDRESS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADDRESS:
                    mAddress = (Address) data.getSerializableExtra("model");
                    binding.tvAddressName.setText(mAddress.getName());
                    binding.tvPhone.setText(mAddress.getPhone());
                    binding.tvAddress.setText(mAddress.getAddress());
                    binding.llyChooseAddress.setVisibility(View.GONE);
                    binding.llyAddress.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void totleFee() {
        BigDecimal totle = new BigDecimal("0");
        for (OrderGoods bus : mOrderGoodsDatas) {
            float price = bus.getGoods().getGoodsPrice() * bus.getCount();
            BigDecimal bp = new BigDecimal(price);
            totle = totle.add(bp);
        }
        BigDecimal b = totle.setScale(2, RoundingMode.HALF_UP);//保留两位小数
        binding.tvTotlePay.setText("" + b.toPlainString());
    }

    private void deleteBusGoods(BusGoods busGoods) {
        busGoods.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                } else {
                    hideLoading();
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }
}
