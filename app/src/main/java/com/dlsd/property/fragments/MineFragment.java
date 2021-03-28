package com.dlsd.property.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlsd.property.MyApp;
import com.dlsd.property.R;
import com.dlsd.property.activitys.address.AddressManagerActivity;
import com.dlsd.property.activitys.base.UserInfoActivity;
import com.dlsd.property.activitys.buy.OrderListActivity;
import com.dlsd.property.activitys.setting.SettingActivity;
import com.dlsd.property.base.BaseFragment;
import com.dlsd.property.databinding.FragmentMineBinding;
import com.dlsd.property.db.User;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMineBinding mBinding;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init() {
        mBinding.ivEdit.setOnClickListener(this);
        mBinding.llyPatientMsg.setOnClickListener(this);
        mBinding.llyAddressManager.setOnClickListener(this);

        mBinding.llyClinicOrder.setOnClickListener(this);
        mBinding.llyClinicAll.setOnClickListener(this);
        mBinding.llyClinicDfk.setOnClickListener(this);
        mBinding.llyClinicDsh.setOnClickListener(this);
        mBinding.llyClinicYwc.setOnClickListener(this);
        mBinding.llyUserInfo.setOnClickListener(this);
        myOwn();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_edit:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.lly_patient_msg:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.lly_address_manager:
                startActivity(new Intent(getActivity(), AddressManagerActivity.class));
                break;

            case R.id.lly_clinic_order:
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                intent.putExtra("state", -1);
                startActivity(intent);
                break;
            case R.id.lly_clinic_all:
                Intent intentall = new Intent(getActivity(), OrderListActivity.class);
                intentall.putExtra("state", -1);
                startActivity(intentall);
                break;
            case R.id.lly_clinic_dfk:
                Intent intent0 = new Intent(getActivity(), OrderListActivity.class);
                intent0.putExtra("state", 0);
                startActivity(intent0);
                break;
            case R.id.lly_clinic_dfh:
                Intent intent1 = new Intent(getActivity(), OrderListActivity.class);
                intent1.putExtra("state", 1);
                startActivity(intent1);
                break;
            case R.id.lly_clinic_dsh:
                Intent intent2 = new Intent(getActivity(), OrderListActivity.class);
                intent2.putExtra("state", 2);
                startActivity(intent2);
                break;
            case R.id.lly_clinic_ywc:
                Intent intent3 = new Intent(getActivity(), OrderListActivity.class);
                intent3.putExtra("state", 3);
                startActivity(intent3);
                break;
            case R.id.lly_user_info:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        myOwn();

    }

    /**
     * 个人中心
     */
    private void myOwn() {
        // 访问网络的方法

        initUserCenter();
    }

    private void initUserCenter() {
        User u = MyApp.mUserLogin;
        if (null == u) {
            showToast("数据错误");
            return;
        }
        if (null != u.getPhone() && u.getPhone().length() == 11) {
            mBinding.tvPhone.setText("" + u.getPhone().substring(0, 3) + "****" + u.getPhone().substring(7, 11));
        } else {
            mBinding.tvPhone.setText("");
        }
        if (null != MyApp.mUserLogin && !TextUtils.isEmpty(MyApp.mUserLogin.getMood()) && !MyApp.mUserLogin.getMood().equals("null")) {
            mBinding.tvMood.setText(MyApp.mUserLogin.getMood() + "");
        }

        if (u.getRole() == 0) {
            //管理员
            mBinding.llyClinicOrder.setVisibility(View.GONE);
            mBinding.llyAddressManager.setVisibility(View.GONE);
        } else {
            //用户
            mBinding.llyClinicOrder.setVisibility(View.VISIBLE);
            mBinding.llyAddressManager.setVisibility(View.VISIBLE);
        }

        mBinding.tvClinicAll.setVisibility(View.GONE);
        mBinding.tvClinicDfk.setVisibility(View.GONE);
        mBinding.tvClinicDsh.setVisibility(View.GONE);
        mBinding.tvClinicYwc.setVisibility(View.GONE);

//        if (mUserDatas.getToConfirm() > 0) {
//            mBinding.tvClinicDqr.setVisibility(View.VISIBLE);
//            mBinding.tvClinicDqr.setText("" + mUserDatas.getToConfirm());
//        } else {
//            mBinding.tvClinicDqr.setVisibility(View.GONE);
//        }
//        if (mUserDatas.getDiagnosing() > 0) {
//            mBinding.tvClinicWzz.setVisibility(View.VISIBLE);
//            mBinding.tvClinicWzz.setText("" + mUserDatas.getDiagnosing());
//        } else {
//            mBinding.tvClinicWzz.setVisibility(View.GONE);
//        }
//        if (mUserDatas.getDiagnosed() > 0) {
//            mBinding.tvClinicYzd.setVisibility(View.VISIBLE);
//            mBinding.tvClinicYzd.setText("" + mUserDatas.getDiagnosed());
//        } else {
//            mBinding.tvClinicYzd.setVisibility(View.GONE);
//        }
//        if (mUserDatas.getAppraised() > 0) {
//            mBinding.tvClinicYkf.setVisibility(View.VISIBLE);
//            mBinding.tvClinicYkf.setText("" + mUserDatas.getAppraised());
//        } else {
//            mBinding.tvClinicYkf.setVisibility(View.GONE);
//        }
//        if (mUserDatas.getDiagnosisRefund() > 0) {
//            mBinding.tvClinicTk.setVisibility(View.VISIBLE);
//            mBinding.tvClinicTk.setText("" + mUserDatas.getDiagnosisRefund());
//        } else {
//            mBinding.tvClinicTk.setVisibility(View.GONE);
//        }
    }
}
