package com.dlsd.property.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.activitys.base.ResetPwdActivity;
import com.dlsd.property.activitys.buy.AllOrderActivity;
import com.dlsd.property.activitys.buy.OrderWaitSendListActivity;
import com.dlsd.property.base.BaseFragment;
import com.dlsd.property.databinding.FragmentGuardMainBinding;
import com.dlsd.property.merchants.activitys.GoodsManagerActivity;
import com.dlsd.property.merchants.activitys.GoodsTypeManagerActivity;
import com.dlsd.property.weight.ImgBanner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;


/**
 * 商家首页HomeFragment
 */
public class MerchantsHomeFragment extends BaseFragment implements View.OnClickListener {

    private FragmentGuardMainBinding mBinding;
    private ArrayList<Integer> mBannerUrl;

    public static MerchantsHomeFragment newInstance() {
        MerchantsHomeFragment fragment = new MerchantsHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_guard_main, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init() {
        mBannerUrl = new ArrayList<Integer>();
        mBinding.llyGoods.setOnClickListener(this);
        mBinding.llyTypes.setOnClickListener(this);
        mBinding.llyUpdatePwd.setOnClickListener(this);
        mBinding.llySend.setOnClickListener(this);
        mBinding.allOrderSend.setOnClickListener(this);
        mBannerUrl.add(R.mipmap.shopbg);
        mBinding.banner.setImages(mBannerUrl).setImageLoader(new ImgBanner())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setBannerAnimation(Transformer.Default)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .start();
        mBinding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        mBinding.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_goods:
                startActivity(new Intent(getActivity(), GoodsManagerActivity.class));
                break;
            case R.id.lly_types:
                startActivity(new Intent(getActivity(), GoodsTypeManagerActivity.class));
                break;
            case R.id.lly_update_pwd:
                Intent intent = new Intent(getActivity(), ResetPwdActivity.class);
                intent.putExtra("phone", MyApp.mUserLogin.getPhone());
                startActivity(intent);
                break;
            case R.id.lly_send:
                startActivity(new Intent(getActivity(), OrderWaitSendListActivity.class));
                break;
            case R.id.all_order_send:
                startActivity(new Intent(getActivity(), AllOrderActivity.class));
                break;

        }
    }
}
