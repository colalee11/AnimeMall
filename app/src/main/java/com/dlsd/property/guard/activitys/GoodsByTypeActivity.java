package com.dlsd.property.guard.activitys;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityGoodsByTypeBinding;
import com.dlsd.property.db.Goods;
import com.dlsd.property.db.GoodsType;
import com.dlsd.property.db.User;
import com.dlsd.property.guard.adapters.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class GoodsByTypeActivity extends BaseActivity {

    private ActivityGoodsByTypeBinding binding;
    private GoodsAdapter mGoodsAdapter;
    private ArrayList<Goods> mGoodsAllDatas = new ArrayList<Goods>();
    private ArrayList<Goods> mGoodsDatas = new ArrayList<Goods>();

    private GoodsType mGoodsType;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goods_by_type);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        mGoodsType = (GoodsType) getIntent().getSerializableExtra("goodsType");
        binding.titlebar.getTvTitle().setText(mGoodsType.getTypeName() + "商品列表");
        mGoodsAdapter = new GoodsAdapter(this, mGoodsDatas);
        mGoodsAdapter.setOnItemClickListener(new GoodsAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(GoodsAdapter adapter, int position) {
                Intent intent = new Intent(GoodsByTypeActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goods", mGoodsDatas.get(position));
                startActivity(intent);
            }
        });
        binding.rcvList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvList.setAdapter(mGoodsAdapter);

        binding.titlebar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsByTypeActivity.this, AddGoodsActivity.class));
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String n = binding.etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(n)) {
                    mGoodsDatas.clear();
                    mGoodsDatas.addAll(mGoodsAllDatas);
                    mGoodsAdapter.notifyDataSetChanged();
                }
            }
        });
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String n = binding.etSearch.getText().toString().trim();
                mGoodsDatas.clear();
                if (TextUtils.isEmpty(n)) {
                    mGoodsDatas.addAll(mGoodsAllDatas);
                } else {
                    for (Goods goods : mGoodsAllDatas) {
                        if (goods.getGoodsName().contains(n)) {
                            mGoodsDatas.add(goods);
                        }
                    }
                }
                mGoodsAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.addWhereEqualTo("goodsType", mGoodsType);
        query.order("-GoodsSeeCount").include("goodsType")
                .findObjects(new FindListener<Goods>() {
                    @Override
                    public void done(List<Goods> object, BmobException e) {
                        if (e == null) {
                            mGoodsDatas.clear();
                            mGoodsAllDatas.clear();
                            mGoodsAllDatas.addAll(object);
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
