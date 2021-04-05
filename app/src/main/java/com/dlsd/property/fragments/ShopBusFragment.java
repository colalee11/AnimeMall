package com.dlsd.property.fragments;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dlsd.property.R;
import com.dlsd.property.activitys.detail.ConfirmOrderDetailActivity;
import com.dlsd.property.adapters.GoodGroupAdapter;
import com.dlsd.property.base.BaseFragment;
import com.dlsd.property.databinding.FragmentShopBusBinding;
import com.dlsd.property.db.BusGoods;
import com.dlsd.property.db.OrderGoods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import zhangphil.iosdialog.widget.AlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopBusFragment extends BaseFragment implements View.OnClickListener {

    private FragmentShopBusBinding mBinding;

    private ArrayList<BusGoods> mBusDatas = new ArrayList<BusGoods>();

    private GoodGroupAdapter goodGroupAdapter;

    private boolean isAllCheck = false;


    public ShopBusFragment() {
        // Required empty public constructor
    }


    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_bus, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init() {
        goodGroupAdapter = new GoodGroupAdapter(getActivity(), mBusDatas);
        goodGroupAdapter.setOnItemClickListener(new GoodGroupAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemDetailClicked(GoodGroupAdapter adapter, int position) {

            }
            //商品是否选中
            @Override
            public void onItemCheckClicked(int position) {
                if (mBusDatas.get(position).isCheck()) {
                    mBusDatas.get(position).setCheck(false);
                } else {
                    mBusDatas.get(position).setCheck(true);
                }
                goodGroupAdapter.notifyDataSetChanged();
                totleFee();
            }

            //长按删除商品
            @Override
            public void onItemLongClicked(int gPostion, int position) {
                showDeleteDialog(mBusDatas.get(position));
            }

            //商品数量加一
            @Override
            public void onItemAddClicked(int position) {
                mBusDatas.get(position).setCount(mBusDatas.get(position).getCount() + 1);
                mBusDatas.get(position).update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (null == e) {
                            goodGroupAdapter.notifyDataSetChanged();
                            totleFee();
                        }
                    }
                });
            }

            //商品数量减一
            @Override
            public void onItemSubClicked(int position) {
                if (mBusDatas.get(position).getCount() > 1) {
                    mBusDatas.get(position).setCount(mBusDatas.get(position).getCount() - 1);
                    mBusDatas.get(position).update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (null == e) {
                                goodGroupAdapter.notifyDataSetChanged();
                                totleFee();
                            }
                        }
                    });
                } else {
                    showDeleteDialog(mBusDatas.get(position));
                }
            }


        });
        mBinding.rcvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.rcvList.setAdapter(goodGroupAdapter);
        getBus();
        mBinding.ivAllCheck.setOnClickListener(this);
        mBinding.tvConfirm.setOnClickListener(this);
    }

    public void setRefresh() {
        getBus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_all_check:
                //是否全选
                if (isAllCheck) {
                    for (BusGoods bus : mBusDatas) {
                        bus.setCheck(false);
                    }
                    goodGroupAdapter.notifyDataSetChanged();
                    mBinding.ivAllCheck.setImageResource(R.drawable.new_off_choice);
                    mBinding.tvTotlePrice.setText("¥ 0.00");
                    isAllCheck = false;
                } else {
                    for (BusGoods bus : mBusDatas) {
                        bus.setCheck(true);
                    }
                    goodGroupAdapter.notifyDataSetChanged();
                    mBinding.ivAllCheck.setImageResource(R.drawable.new_on_choice);
                    totleFee();
                    isAllCheck = true;
                }
                break;
            case R.id.tv_confirm:
                ArrayList<OrderGoods> ogList = new ArrayList<OrderGoods>();
                ArrayList<BusGoods> bgList = new ArrayList<BusGoods>();
                for (BusGoods bus : mBusDatas) {
                    if (bus.isCheck()) {
                        OrderGoods og = new OrderGoods();
                        og.setCount(bus.getCount());
                        og.setGoods(bus.getGoods());
                        ogList.add(og);
                        bgList.add(bus);
                    }
                }
                if (ogList.size() > 0) {
                    Intent intent = new Intent(getActivity(), ConfirmOrderDetailActivity.class);
                    intent.putExtra("orderGoods", ogList);
                    intent.putExtra("busGoods", bgList);
                    startActivity(intent);
                } else {
                    showToast("请勾选商品");
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getBus();
    }

    /**
     * 删除商品数量提示框
     *
     * @param busGoods
     */
    private void showDeleteDialog(BusGoods busGoods) {
        AlertDialog dialog = new AlertDialog(getActivity()).builder();
        dialog.setTitle("温馨提示")
                .setMsg("是否将 " + busGoods.getGoods().getGoodsName() + " 从购物车移除？")
                .setNegativeButton("取消", "#666666", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setPositiveButton("确认", "#5E76EF", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteBusGoods(busGoods);
                    }
                }).show();
    }

    /*
     *合计选中商品价格
     *
     */
    private void totleFee() {
        BigDecimal totle = new BigDecimal("0");
        for (BusGoods bus : mBusDatas) {
            if (bus.isCheck()) {
                float price = bus.getGoods().getGoodsPrice() * bus.getCount();
                BigDecimal bp = new BigDecimal(price);
                totle = totle.add(bp);
            }
        }
        BigDecimal b = totle.setScale(2, RoundingMode.HALF_UP);//保留两位小数
        mBinding.tvTotlePrice.setText("¥ " + b.toPlainString());
    }

    /**
     * 获得总共宝贝数量
     */
    private void getBus() {
        BmobQuery<BusGoods> query = new BmobQuery<BusGoods>();
        query.include("goods,user");//关联查询
        query.findObjects(new FindListener<BusGoods>() {//查询购物车多条数据
            @Override
            public void done(List<BusGoods> object, BmobException e) {
                if (e == null) {
                    mBusDatas.clear();
                    if (object.size() > 0) {
                        mBusDatas.addAll(object);//添加数据
                    }
                    for (BusGoods bus : mBusDatas) {
                        bus.setCheck(false);
                    }
                    mBinding.tvTotleGoods.setText("共" + mBusDatas.size() + "件宝贝");
                    goodGroupAdapter.notifyDataSetChanged();
                } else {
                    // showToast(getString(R.string.error_msg));
                }
            }
        });
    }

    /**
     * 删除商品数量
     *
     * @param busGoods
     */
    private void deleteBusGoods(BusGoods busGoods) {
        busGoods.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showToast("已移除");
                    getBus();
                } else {
                    //showToast(getString(R.string.error_msg));
                }
            }
        });
    }
}
