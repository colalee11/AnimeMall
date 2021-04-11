package com.dlsd.property.merchants.activitys;

import android.content.Intent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityGoodsManagerBinding;
import com.dlsd.property.db.Goods;
import com.dlsd.property.merchants.adapters.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class GoodsManagerActivity extends BaseActivity {

    private ActivityGoodsManagerBinding binding;
    private GoodsAdapter mGoodsAdapter;
    private ArrayList<Goods> mGoodsDatas = new ArrayList<Goods>();

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goods_manager);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);

        mGoodsAdapter = new GoodsAdapter(this, mGoodsDatas);
        mGoodsAdapter.setOnItemClickListener(new GoodsAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(GoodsAdapter adapter, int position) {
                Intent intent = new Intent(GoodsManagerActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goods", mGoodsDatas.get(position));
                startActivity(intent);
            }
        });
        binding.rcvList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvList.setAdapter(mGoodsAdapter);

        binding.titlebar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsManagerActivity.this, AddGoodsActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.order("-createdAt").include("goodsType")
                .findObjects(new FindListener<Goods>() {
                    @Override
                    public void done(List<Goods> object, BmobException e) {
                        if (e == null) {
                            mGoodsDatas.clear();
                            mGoodsDatas.addAll(object);
                            if (mGoodsDatas.size() > 0) {
                                binding.rcvList.setVisibility(View.VISIBLE);
                                binding.llyNone.setVisibility(View.GONE);
                            } else {
                                binding.rcvList.setVisibility(View.GONE);
                                binding.llyNone.setVisibility(View.VISIBLE);
                            }
                            mGoodsAdapter.notifyDataSetChanged();
                        } else {
                            showToast(getString(R.string.error_msg));
                        }
                        hideLoading();
                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }
}
