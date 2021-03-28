package com.dlsd.property.activitys.buy;

import android.view.View;

import com.dlsd.property.R;
import com.dlsd.property.adapters.OrderSendAdapter;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityOrderSendListBinding;
import com.dlsd.property.db.Order;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import zhangphil.iosdialog.widget.AlertDialog;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

/**
 * 发货列表
 */
public class OrderWaitSendListActivity extends BaseActivity {

    private ActivityOrderSendListBinding binding;

    private ArrayList<Order> mOrderDatas = new ArrayList<Order>();

    private OrderSendAdapter mOrderAdapter;

    private int mIndex = 1;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_send_list);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        mIndex = 1;
        mOrderAdapter = new OrderSendAdapter(OrderWaitSendListActivity.this, mOrderDatas);
        mOrderAdapter.setOnItemClickListener(new OrderSendAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemConfirmClicked(OrderSendAdapter adapter, int position) {
                // 0 待支付  1待发货  2待收货  3已完成  4已取消   5已退货
                Order order = mOrderDatas.get(position);
                showSendDialog(order, 2);
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

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getOrder();
    }

    private void showSendDialog(Order order, int state) {
        AlertDialog dialog = new AlertDialog(OrderWaitSendListActivity.this).builder();
        dialog.setTitle("温馨提示")
                .setMsg("是否确认发货")
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


    private void updateState(Order order, int state) {
        //0 待支付  1待发货  2待收货  3已完成  4已取消   5已退货
        switch (state) {
            case 2:
                order.setSendTime(System.currentTimeMillis());
                order.setState(2);
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
