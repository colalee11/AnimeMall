package com.dlsd.property.activitys.detail;

import android.content.Intent;
import android.view.View;

import com.dlsd.property.R;
import com.dlsd.property.adapters.GoodItemsConfirmAdapter;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityBuyOrderDetailBinding;
import com.dlsd.property.db.Order;
import com.dlsd.property.db.OrderGoods;
import com.dlsd.property.wxapi.PayActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import zhangphil.iosdialog.widget.AlertDialog;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class BuyOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityBuyOrderDetailBinding binding;

    private ArrayList<OrderGoods> mOrderGoodsDatas = new ArrayList<OrderGoods>();
    private GoodItemsConfirmAdapter mGoodItemsConfirmAdapter;

    private Order mOrder;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buy_order_detail);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        binding.btnConfirm.setOnClickListener(this);
        binding.btnPay.setOnClickListener(this);
        binding.btnRefuse.setOnClickListener(this);
        mGoodItemsConfirmAdapter = new GoodItemsConfirmAdapter(BuyOrderDetailActivity.this, mOrderGoodsDatas);
        binding.rcvGoodsList.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvGoodsList.setAdapter(mGoodItemsConfirmAdapter);
        mOrder = (Order) getIntent().getSerializableExtra("order");
    }

    @Override
    protected void initData() {
        mOrderGoodsDatas.clear();
        mOrderGoodsDatas.addAll(mOrder.getGoodsList());
        mGoodItemsConfirmAdapter.notifyDataSetChanged();
        totleFee();

        binding.tvAddressName.setText(mOrder.getAddress().getName());
        binding.tvPhone.setText(mOrder.getAddress().getPhone());
        binding.tvAddress.setText(mOrder.getAddress().getAddress());

        switch (mOrder.getState()) {
            case 0:
                binding.tvOrderState.setText("待支付");
                binding.llyBtn.setVisibility(View.VISIBLE);
                binding.btnPay.setVisibility(View.VISIBLE);
                binding.btnRefuse.setVisibility(View.VISIBLE);
                binding.btnConfirm.setVisibility(View.GONE);
                break;
            case 1:
                binding.tvOrderState.setText("待发货");
                binding.llyBtn.setVisibility(View.GONE);
                binding.btnPay.setVisibility(View.GONE);
                binding.btnRefuse.setVisibility(View.GONE);
                binding.btnConfirm.setVisibility(View.GONE);
                break;
            case 2:
                binding.tvOrderState.setText("待收货");
                binding.llyBtn.setVisibility(View.VISIBLE);
                binding.btnPay.setVisibility(View.GONE);
                binding.btnRefuse.setVisibility(View.VISIBLE);
                binding.btnConfirm.setVisibility(View.VISIBLE);
                break;
            case 3:
                binding.tvOrderState.setText("已完成");
                binding.llyBtn.setVisibility(View.GONE);
                binding.btnPay.setVisibility(View.GONE);
                binding.btnRefuse.setVisibility(View.GONE);
                binding.btnConfirm.setVisibility(View.GONE);
                break;
            case 4:
                binding.tvOrderState.setText("已取消");
                binding.llyBtn.setVisibility(View.GONE);
                binding.btnPay.setVisibility(View.GONE);
                binding.btnRefuse.setVisibility(View.GONE);
                binding.btnConfirm.setVisibility(View.GONE);
                break;
            case 5:
                binding.tvOrderState.setText("已退货");
                binding.llyBtn.setVisibility(View.GONE);
                binding.btnPay.setVisibility(View.GONE);
                binding.btnRefuse.setVisibility(View.GONE);
                binding.btnConfirm.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        binding.tvOrderId.setText(mOrder.getObjectId());
        binding.tvOrderCreateTime.setText(getDateToString(mOrder.getCreateTime()));
        if (mOrder.getPayTime() > 0) {
            binding.llyPayTime.setVisibility(View.VISIBLE);
            binding.llyPayWay.setVisibility(View.VISIBLE);
            binding.tvOrderPayTime.setText(getDateToString(mOrder.getPayTime()));
            //0 支付宝  1 微信
            if (mOrder.getPayWay() == 0) {
                binding.tvOrderPayWay.setText("支付宝");
            } else {
                binding.tvOrderPayWay.setText("微信");
            }
        } else {
            binding.llyPayWay.setVisibility(View.GONE);
            binding.llyPayTime.setVisibility(View.GONE);
        }

        if (mOrder.getFinishTime() > 0) {
            binding.llyFinishTime.setVisibility(View.VISIBLE);
            binding.tvOrderFinishTime.setText(getDateToString(mOrder.getFinishTime()));
        } else {
            binding.llyFinishTime.setVisibility(View.GONE);
        }
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                mOrder.setState(3);
                showConfirmDialog(3);
                break;
            case R.id.btn_pay:
                Intent intent = new Intent(BuyOrderDetailActivity.this, PayActivity.class);
                intent.putExtra("orderId", mOrder.getObjectId());
                intent.putExtra("totleFee", "" + mOrder.getTotleFee());
                startActivity(intent);
                break;
            case R.id.btn_refuse:
                if (mOrder.getState() == 0) {
                    //待支付 --取消 -> 已取消
                    mOrder.setState(4);
                    showCancelDialog(4);
                } else {
                    //待收货 --取消 -> 已退货
                    mOrder.setState(5);
                    showCancelDialog(5);
                }
                break;
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

    private void showConfirmDialog(int state) {
        AlertDialog dialog = new AlertDialog(BuyOrderDetailActivity.this).builder();
        dialog.setTitle("温馨提示")
                .setMsg("是否确认收货")
                .setNegativeButton("取消", "#666666", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setPositiveButton("确认", "#5E76EF", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateState(state);
                    }
                }).show();
    }

    private void showCancelDialog(int state) {
        AlertDialog dialog = new AlertDialog(BuyOrderDetailActivity.this).builder();
        dialog.setTitle("温馨提示")
                .setMsg("是否取消订单")
                .setNegativeButton("取消", "#666666", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setPositiveButton("确认", "#5E76EF", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateState(state);
                    }
                }).show();
    }

    private void updateState(int state) {
        //0 待支付   1待收货  2已完成  3已取消   4已退货
        switch (state) {
            case 1:
                mOrder.setPayTime(System.currentTimeMillis());
                break;
            case 2:
                mOrder.setSendTime(System.currentTimeMillis());
                break;
            case 3:
                mOrder.setFinishTime(System.currentTimeMillis());
                break;
            case 4:
                mOrder.setFinishTime(System.currentTimeMillis());
                break;
            case 5:
                mOrder.setFinishTime(System.currentTimeMillis());
                break;
        }
        mOrder.update(mOrder.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    showToast("操作成功");
                    initData();
                } else {
                    showToast(R.string.error_msg);
                }
            }
        });
    }
}
