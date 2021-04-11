package com.dlsd.property.merchants.activitys;

import android.content.Intent;
import android.view.View;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityGoodsTypeManagerBinding;
import com.dlsd.property.db.Goods;
import com.dlsd.property.db.GoodsType;
import com.dlsd.property.merchants.adapters.GoodsTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import zhangphil.iosdialog.widget.AlertDialog;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class GoodsTypeManagerActivity extends BaseActivity {

    private ActivityGoodsTypeManagerBinding binding;
    private ArrayList<GoodsType> mGoodsTypesDatas = new ArrayList<>();
    private GoodsTypeAdapter mGoodsTypeAdapter;
    private ArrayList<Goods> mGoodsDatas = new ArrayList<Goods>();

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goods_type_manager);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        mGoodsTypeAdapter = new GoodsTypeAdapter(this, mGoodsTypesDatas);
        mGoodsTypeAdapter.setOnItemClickListener(new GoodsTypeAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemGoodsClicked(GoodsTypeAdapter adapter, int position) {
                Intent intent = new Intent(GoodsTypeManagerActivity.this, GoodsByTypeActivity.class);
                intent.putExtra("goodsType", mGoodsTypesDatas.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClicked(GoodsTypeAdapter adapter, int position) {
                AlertDialog dialog = new AlertDialog(GoodsTypeManagerActivity.this).builder();
                dialog.setTitle("温馨提示")
                        .setMsg("是否确认删除分类：" + mGoodsTypesDatas.get(position).getTypeName())
                        .setNegativeButton("取消", "#666666", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setPositiveButton("确认", "#5E76EF", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                BmobQuery<Goods> bmobQuery1 = new BmobQuery<Goods>();
                                bmobQuery1.addWhereEqualTo("goodsType", mGoodsTypesDatas.get(position));
                                bmobQuery1.findObjects(new FindListener<Goods>() {
                                    @Override
                                    public void done(List<Goods> object, BmobException e) {
                                        if (e == null) {
                                            if (object.size() > 0) {
                                                showToast("因该分类下存在商品，不可删除！");
                                            } else {
                                                deleteType(mGoodsTypesDatas.get(position));
                                            }
                                        } else {
                                            showToast(getString(R.string.error_msg));
                                        }
                                    }
                                });
                            }
                        }).show();
            }
        });
        binding.rcvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvList.setAdapter(mGoodsTypeAdapter);
        binding.titlebar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsTypeManagerActivity.this, AddTypeActivity.class));
            }
        });
    }

    private void deleteType(GoodsType goodsType) {
        goodsType.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    showToast("删除成功");
                    initData();
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        BmobQuery<GoodsType> query = new BmobQuery<GoodsType>();
        query.order("-createdAt")
                .findObjects(new FindListener<GoodsType>() {
                    @Override
                    public void done(List<GoodsType> object, BmobException e) {
                        if (e == null) {
                            mGoodsTypesDatas.clear();
                            mGoodsTypesDatas.addAll(object);
                            if (mGoodsTypesDatas.size() > 0) {
                                binding.rcvList.setVisibility(View.VISIBLE);
                                binding.llyNone.setVisibility(View.GONE);
                            } else {
                                binding.rcvList.setVisibility(View.GONE);
                                binding.llyNone.setVisibility(View.VISIBLE);
                            }
                            for (GoodsType type : mGoodsTypesDatas) {
                                type.setGoodsCount(0);
                            }
                            mGoodsTypeAdapter.notifyDataSetChanged();
                            BmobQuery<Goods> query = new BmobQuery<Goods>();
                            query.order("-createdAt").include("goodsType")
                                    .findObjects(new FindListener<Goods>() {
                                        @Override
                                        public void done(List<Goods> object, BmobException e) {
                                            if (e == null) {
                                                mGoodsDatas.clear();
                                                mGoodsDatas.addAll(object);
                                                for (Goods goods : mGoodsDatas) {
                                                    for (GoodsType type : mGoodsTypesDatas) {
                                                        if (goods.getGoodsType().getTypeId().equals(type.getTypeId())) {
                                                            type.setGoodsCount(type.getGoodsCount() + 1);
                                                        }
                                                    }
                                                }
                                                mGoodsTypeAdapter.notifyDataSetChanged();
                                            } else {
                                                showToast(getString(R.string.error_msg));
                                            }
                                            hideLoading();
                                        }
                                    });
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
