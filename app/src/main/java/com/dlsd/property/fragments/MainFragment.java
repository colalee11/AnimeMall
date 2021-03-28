package com.dlsd.property.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.activitys.MainActivity;
import com.dlsd.property.adapters.MainTypeAdapter;
import com.dlsd.property.base.BaseFragment;
import com.dlsd.property.databinding.FragmentMainBinding;
import com.dlsd.property.db.Goods;
import com.dlsd.property.db.GoodsType;
import com.dlsd.property.guard.activitys.GoodsByTypeActivity;
import com.dlsd.property.guard.activitys.GoodsDetailActivity;
import com.dlsd.property.guard.adapters.GoodsAdapter;
import com.dlsd.property.weight.ImgBanner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * 首页HomeFragment
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_DETAIL = 1001;

    private FragmentMainBinding mBinding;
    private ArrayList<GoodsType> mTypeDatas = new ArrayList<GoodsType>();
    private MainTypeAdapter mMainTypeAdapter;
    private ArrayList<Integer> mBannerUrl;
    private GoodsAdapter mGoodsAdapter;
    private ArrayList<Goods> mGoodsDatas = new ArrayList<Goods>();
    AlertDialog alertDialog;
    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init() {
        getTypeAll();
        getGoods();
        fresh();
        mMainTypeAdapter = new MainTypeAdapter(getActivity(), mTypeDatas);
        mBannerUrl = new ArrayList<Integer>();
        mMainTypeAdapter.setHasStableIds(true);
        mMainTypeAdapter.setOnItemClickListener(new MainTypeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(MainTypeAdapter adapter, int position) {
                Intent intent = new Intent(getActivity(), GoodsByTypeActivity.class);
                intent.putExtra("goodsType", mTypeDatas.get(position));
                startActivity(intent);
            }
        });
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.rcvListType.setLayoutManager(linearLayoutManager);
        mBinding.rcvListType.setAdapter(mMainTypeAdapter);

        mGoodsAdapter = new GoodsAdapter(getActivity(), mGoodsDatas);
        mGoodsAdapter.setOnItemClickListener(new GoodsAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(GoodsAdapter adapter, int position) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goods", mGoodsDatas.get(position));
                startActivityForResult(intent, REQUEST_CODE_DETAIL);
            }
        });
        mBinding.rcvList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mBinding.rcvList.setAdapter(mGoodsAdapter);

        mBannerUrl.add(R.mipmap.banner1);
        mBannerUrl.add(R.mipmap.banner2);
        mBannerUrl.add(R.mipmap.banner3);
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

        }
    }
    @SuppressLint("ResourceAsColor")
    private void fresh() {
        mBinding.swipeRefreshFans.setColorSchemeColors(R.color.black);
        mBinding.swipeRefreshFans.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTypeAll();
                getGoods();
                mBinding.swipeRefreshFans.setRefreshing(false);
                showToast("刷新成功");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_DETAIL:
                getGoods();
                break;
        }
    }

    private void getTypeAll() {
        ((MainActivity) getActivity()).showLoading();
        BmobQuery<GoodsType> query = new BmobQuery<GoodsType>();
        query.order("-createdAt")
                .findObjects(new FindListener<GoodsType>() {
                    @Override
                    public void done(List<GoodsType> object, BmobException e) {
                        if (e == null) {
                            mTypeDatas.clear();
                            mTypeDatas.addAll(object);
                            if (mTypeDatas.size() > 0) {
                                mBinding.rcvListType.setVisibility(View.VISIBLE);
                                ((MainActivity) getActivity()).hideLoading();
                            } else {
                                mBinding.rcvListType.setVisibility(View.GONE);
                                ((MainActivity) getActivity()).hideLoading();
                           }
                            mMainTypeAdapter.notifyDataSetChanged();

                        } else {
                            ((MainActivity) getActivity()).hideLoading();
                        }
                        ((MainActivity) getActivity()).hideLoading();
                    }
                });
    }


    public void getGoods() {
        ((MainActivity) getActivity()).showLoading();
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        if (null != MyApp.mUserLogin.getRecentType()) {
            query.addWhereEqualTo("GoodsType", MyApp.mUserLogin.getRecentType());
        }
        query.order("-GoodsSeeCount").include("goodsType")
                .findObjects(new FindListener<Goods>() {
                    @Override
                    public void done(List<Goods> object, BmobException e) {

                        if (e == null) {
                            mGoodsDatas.clear();
                            mGoodsDatas.addAll(object);
                            mGoodsAdapter.notifyDataSetChanged();
                            if (mGoodsDatas.size() == 0) {
                                BmobQuery<Goods> query = new BmobQuery<Goods>();
                                query.order("-GoodsSeeCount").include("goodsType")
                                        .findObjects(new FindListener<Goods>() {
                                            @Override
                                            public void done(List<Goods> object, BmobException e) {
                                                if (e == null) {
                                                    mGoodsDatas.clear();
                                                    mGoodsDatas.addAll(object);
                                                    mGoodsAdapter.notifyDataSetChanged();
                                                    ((MainActivity) getActivity()).hideLoading();
                                                } else {
                                                     showToast(getString(R.string.error_msg));
                                                    ((MainActivity) getActivity()).hideLoading();
                                                }
                                            }
                                        });
                            }
                        } else {
                            showToast(getString(R.string.error_msg));
                        }
                        ((MainActivity) getActivity()).hideLoading();
                    }
                });
    }

}
