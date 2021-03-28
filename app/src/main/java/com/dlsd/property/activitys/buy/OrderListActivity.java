package com.dlsd.property.activitys.buy;

import android.content.Intent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dlsd.property.R;
import com.dlsd.property.activitys.detail.BuyOrderDetailActivity;
import com.dlsd.property.adapters.OrderAdapter;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityOrderListBinding;
import com.dlsd.property.db.Order;
import com.dlsd.property.wxapi.PayActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import zhangphil.iosdialog.widget.AlertDialog;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

/**
 * 订单列表
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener {

    private ActivityOrderListBinding binding;

    private ArrayList<Order> mOrderDatas = new ArrayList<Order>();

    private OrderAdapter mOrderAdapter;

    private int mIndex = -1;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        mIndex = getIntent().getIntExtra("state", -1);
        binding.tvAll.setOnClickListener(this);
        binding.tvDfk.setOnClickListener(this);
        binding.tvDfh.setOnClickListener(this);
        binding.tvDsh.setOnClickListener(this);
        binding.tvYwc.setOnClickListener(this);
        binding.tvYwc.setOnClickListener(this);
        mOrderAdapter = new OrderAdapter(OrderListActivity.this, mOrderDatas);
        mOrderAdapter.setOnItemClickListener(new OrderAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(OrderAdapter adapter, int position) {
                Intent intent = new Intent(OrderListActivity.this, BuyOrderDetailActivity.class);
                intent.putExtra("order", mOrderDatas.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemDetailClicked(int groupPosition, int childPosition) {

            }

            @Override
            public void onItemPayClicked(OrderAdapter adapter, int position) {
                Order order = mOrderDatas.get(position);
                Intent payIntent = new Intent(OrderListActivity.this, PayActivity.class);
                payIntent.putExtra("orderId", order.getObjectId());
                payIntent.putExtra("totleFee", "" + order.getTotleFee());
                startActivity(payIntent);

            }

            @Override
            public void onItemRefuseClicked(OrderAdapter adapter, int position) {
                // 0 待支付  1待发货  2待收货  3已完成  4已取消   5已退货
                Order order = mOrderDatas.get(position);
                if (mOrderDatas.get(position).getState() == 0) {
                    //待支付 --取消 -> 已取消
                    order.setState(4);
                    showCancelDialog(order, 4);
                } else {
                    //待收货 --取消 -> 已退货
                    order.setState(5);
                    showCancelDialog(order, 5);
                }

            }

            @Override
            public void onItemConfirmClicked(OrderAdapter adapter, int position) {
                Order order = mOrderDatas.get(position);
                //收货
                order.setState(3);
                showConfirmDialog(order, 3);
            }
        });
        binding.rcvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvList.setAdapter(mOrderAdapter);


        //下拉刷新的圆圈是否显示
        binding.swiperefreshlayout.setRefreshing(false);
        //设置下拉时圆圈的颜色（可以由多种颜色拼成）
        binding.swiperefreshlayout.setColorSchemeResources(R.color.theme_txt_color);
        //设置下拉时圆圈的背景颜色（这里设置成白色）
        binding.swiperefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        //设置下拉刷新时的操作
        binding.swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //具体操作
                getOrder();
            }
        });
        getOrder();
    }

    @Override
    protected void initData() {
        hideAllBar();
        switch (mIndex) {
            case -1:
                binding.tvAll.setTextColor(getResources().getColor(R.color.theme_txt_color));
                break;
            case 0:
                binding.tvDfk.setTextColor(getResources().getColor(R.color.theme_txt_color));
                break;
            case 1:
                binding.tvDfh.setTextColor(getResources().getColor(R.color.theme_txt_color));
                break;
            case 2:
                binding.tvDsh.setTextColor(getResources().getColor(R.color.theme_txt_color));
                break;
            case 3:
                binding.tvYwc.setTextColor(getResources().getColor(R.color.theme_txt_color));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getOrder();
    }

    private void showConfirmDialog(Order order, int state) {
        AlertDialog dialog = new AlertDialog(OrderListActivity.this).builder();
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
                        updateState(order, state);
                    }
                }).show();
    }

    private void showCancelDialog(Order order, int state) {
        AlertDialog dialog = new AlertDialog(OrderListActivity.this).builder();
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
                        updateState(order, state);
                    }
                }).show();
    }

    private void getOrder() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        if (mIndex >= 0) {
            query.addWhereEqualTo("state", mIndex);
        }
        query.order("-createdAt").include("address").findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                binding.swiperefreshlayout.setRefreshing(false);
                if (e == null) {
                    mOrderDatas.clear();
                    if (object.size() > 0) {
                        mOrderDatas.addAll(object);
                        binding.swiperefreshlayout.setVisibility(View.VISIBLE);
                        binding.llyNone.setVisibility(View.GONE);
                    } else {
                        binding.swiperefreshlayout.setVisibility(View.GONE);
                        binding.llyNone.setVisibility(View.VISIBLE);
                    }
                    mOrderAdapter.notifyDataSetChanged();
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }

    /*
     * 订单状态
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                hideAllBar();
                binding.tvAll.setTextColor(getResources().getColor(R.color.theme_txt_color));
                mIndex = -1;
                getOrder();
                break;
            case R.id.tv_dfk:
                hideAllBar();
                binding.tvDfk.setTextColor(getResources().getColor(R.color.theme_txt_color));
                mIndex = 0;
                getOrder();
                break;
            case R.id.tv_dfh:
                hideAllBar();
                binding.tvDfh.setTextColor(getResources().getColor(R.color.theme_txt_color));
                mIndex = 1;
                getOrder();
                break;
            case R.id.tv_dsh:
                hideAllBar();
                binding.tvDsh.setTextColor(getResources().getColor(R.color.theme_txt_color));
                mIndex = 2;
                getOrder();
                break;
            case R.id.tv_ywc:
                hideAllBar();
                binding.tvYwc.setTextColor(getResources().getColor(R.color.theme_txt_color));
                mIndex = 3;
                getOrder();
                break;
            default:
                break;
        }
    }

    private void hideAllBar() {
        binding.tvAll.setTextColor(getResources().getColor(R.color.txt_color_6));
        binding.tvDfk.setTextColor(getResources().getColor(R.color.txt_color_6));
        binding.tvDfh.setTextColor(getResources().getColor(R.color.txt_color_6));
        binding.tvDsh.setTextColor(getResources().getColor(R.color.txt_color_6));
        binding.tvYwc.setTextColor(getResources().getColor(R.color.txt_color_6));
    }

    private void updateState(Order order, int state) {
        //0 待支付   1待收货  2已完成  3已取消   4已退货
        switch (state) {
            case 1:
                order.setPayTime(System.currentTimeMillis());
                break;
            case 2:
                order.setSendTime(System.currentTimeMillis());
                break;
            case 3:
                order.setFinishTime(System.currentTimeMillis());
                break;
            case 4:
                order.setFinishTime(System.currentTimeMillis());
                break;
            case 5:
                order.setFinishTime(System.currentTimeMillis());
                break;
        }
        order.update(order.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    showToast("操作成功");
                    getOrder();
                } else {
                    showToast(R.string.error_msg);
                }
            }
        });
    }
}
