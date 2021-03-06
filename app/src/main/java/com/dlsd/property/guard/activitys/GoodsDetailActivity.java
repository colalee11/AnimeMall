package com.dlsd.property.guard.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.dlsd.property.R;
import com.dlsd.property.activitys.detail.ConfirmOrderDetailActivity;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityGoodsDetailBinding;
import com.dlsd.property.db.BusGoods;
import com.dlsd.property.db.Goods;
import com.dlsd.property.db.OrderGoods;
import com.dlsd.property.weight.ImgBanner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import zhangphil.iosdialog.widget.AlertDialog;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_EDIT = 1001;
    private ActivityGoodsDetailBinding binding;
    private ArrayList<Integer> mBannerUrl = new ArrayList<Integer>();
    private Goods mGoods;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goods_detail);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        mGoods = (Goods) getIntent().getSerializableExtra("goods");
        mBannerUrl.add(R.mipmap.tishi);
        mBannerUrl.add(R.mipmap.tishi);
        mBannerUrl.add(R.mipmap.tishi);
        binding.banner.setImages(mBannerUrl).setImageLoader(new ImgBanner())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setBannerAnimation(Transformer.Default)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .start();
        binding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        binding.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        binding.tvGoodsName.setText(mGoods.getGoodsName());
        binding.tvGoodsIntroduction.setText(mGoods.getGoodsIntroduction());
        binding.tvGoodsPrice.setText("??" + mGoods.getGoodsPrice());
        binding.tvSeeCount.setText("??????: " + (mGoods.getGoodsSeeCount() + 1));
        binding.tvEdit.setOnClickListener(this);
        binding.tvDelete.setOnClickListener(this);
        binding.tvBus.setOnClickListener(this);
        binding.tvBuy.setOnClickListener(this);
        mGoods.setGoodsSeeCount(mGoods.getGoodsSeeCount() + 1);
        mGoods.update(mGoods.getObjectId(), new UpdateListener() {//????????????
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    //showToast("????????????+1");
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
        //????????????????????????
        if (mUser.getRole() == 0) {
            binding.llyManager.setVisibility(View.VISIBLE);
            binding.llyUser.setVisibility(View.GONE);
        } else {
            binding.llyManager.setVisibility(View.GONE);
            binding.llyUser.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        mUser.setRecentType(mGoods.getGoodsType());
        mUser.update(mUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(null == e){

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                Intent intent = new Intent(GoodsDetailActivity.this, GoodsEditActivity.class);
                intent.putExtra("goods", mGoods);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
            case R.id.tv_delete:
                AlertDialog dialog = new AlertDialog(GoodsDetailActivity.this).builder();
                dialog.setTitle("????????????")
                        .setMsg("???????????????????????????" + mGoods.getGoodsName())
                        .setNegativeButton("??????", "#666666", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setPositiveButton("??????", "#5E76EF", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mGoods.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (null == e) {
                                            showToast("????????????");
                                            finish();
                                        } else {
                                            showToast(getString(R.string.error_msg));
                                        }
                                    }
                                });
                            }
                        }).show();
                break;
            case R.id.tv_bus:
                checkBusGoods(mGoods);
                break;
            case R.id.tv_buy:
                OrderGoods og = new OrderGoods();
                og.setGoods(mGoods);
                og.setCount(1);
                ArrayList<OrderGoods> list = new ArrayList<OrderGoods>();
                list.add(og);
                Intent intent1 = new Intent(GoodsDetailActivity.this, ConfirmOrderDetailActivity.class);
                intent1.putExtra("orderGoods", list);
                startActivity(intent1);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_EDIT:
                    mGoods = (Goods) data.getSerializableExtra("goods");
                    binding.tvGoodsName.setText(mGoods.getGoodsName());
                    binding.tvGoodsIntroduction.setText(mGoods.getGoodsIntroduction());
                    binding.tvGoodsPrice.setText("??" + mGoods.getGoodsPrice());
                    break;
            }
        }
    }

    private void checkBusGoods(Goods goods) {
        // ?????????????????????
        BmobQuery<BusGoods> bmobQuery1 = new BmobQuery<BusGoods>();
        bmobQuery1.addWhereEqualTo("user", mUser);
        BmobQuery<BusGoods> bmobQuery2 = new BmobQuery<BusGoods>();
        bmobQuery2.addWhereEqualTo("goods", goods);
        //?????????????????????and??????
        List<BmobQuery<BusGoods>> andQuerys = new ArrayList<BmobQuery<BusGoods>>();
        andQuerys.add(bmobQuery1);
        andQuerys.add(bmobQuery2);
//??????????????????and????????????
        BmobQuery<BusGoods> query = new BmobQuery<BusGoods>();
        query.and(andQuerys);
        query.findObjects(new FindListener<BusGoods>() {
            @Override
            public void done(List<BusGoods> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        //?????????????????????  ????????????+1
                        BusGoods gbus = object.get(0);
                        gbus.setCount(gbus.getCount() + 1);
                        gbus.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (null == e) {
                                    showToast("????????????");
                                } else {
                                    showToast("????????????");
                                }
                            }
                        });
                    } else {
                        //????????????????????? ??????
                        BusGoods gbus = new BusGoods();
                        gbus.setCount(1);
                        gbus.setGoods(goods);
                        gbus.setUser(mUser);
                        gbus.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (null == e) {
                                    showToast("????????????");
                                } else {
                                    showToast("????????????");
                                }
                            }
                        });
                    }
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }
}
