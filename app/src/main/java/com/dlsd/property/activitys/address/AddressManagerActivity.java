package com.dlsd.property.activitys.address;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dlsd.property.R;
import com.dlsd.property.adapters.AddressAdapter;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityAddressListBinding;
import com.dlsd.property.db.Address;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import zhangphil.iosdialog.widget.AlertDialog;

/**
 * 地址管理
 */
public class AddressManagerActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAddressListBinding binding;


    private AddressAdapter mAdapter;
    private ArrayList<Address> mDatas;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_list);
    }

    @Override
    protected void initView() {
        mDatas = new ArrayList<Address>();
        mAdapter = new AddressAdapter(AddressManagerActivity.this, mDatas);
        mAdapter.setOnItemClickListener(new AddressAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemEditClicked(AddressAdapter adapter, int position) {
                Intent intent = new Intent(AddressManagerActivity.this, EditAddressActivity.class);
                intent.putExtra("model", mDatas.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClicked(AddressAdapter adapter, int position) {

            }

            @Override
            public void onItemLongClicked(AddressAdapter adapter, int position) {
                showDeleteDialog(position);
            }
        });
        binding.rcvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvList.setAdapter(mAdapter);

        binding.titlebar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressManagerActivity.this, AddAddressActivity.class));
            }
        });
    }


    private void showDeleteDialog(int position) {
        AlertDialog dialog = new AlertDialog(AddressManagerActivity.this).builder();
        dialog.setTitle("提示")
                .setMsg("是否删除选中地址?")
                .setNegativeButton("否", "#666666", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("是", "#5E76EF", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteAddress(position);
                    }
                }).show();
    }

    @Override
    protected void initData() {
        binding.titlebar.setTitle("地址管理");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lsh", "addressList---刷新");
        getAddressList();
    }


    private void deleteAddress(int position) {
        Address address = mDatas.get(position);
        address.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mDatas.remove(position);
                    mAdapter.notifyDataSetChanged();
                    showToast("删除成功");
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }

    /**
     * 获取地址
     */
    private void getAddressList() {
        showLoading();
        // 访问网络的方法
        BmobQuery<Address> query = new BmobQuery<Address>();
        query.addWhereEqualTo("user", mUser);
        query.include("user");
        query.findObjects(new FindListener<Address>() {
            @Override
            public void done(List<Address> object, BmobException e) {
                if (e == null) {
                    mDatas.clear();
                    if (object.size() > 0) {
                        mDatas.addAll(object);
                        binding.llyNone.setVisibility(View.GONE);
                        binding.rcvList.setVisibility(View.VISIBLE);
                    } else {
                        binding.llyNone.setVisibility(View.VISIBLE);
                        binding.rcvList.setVisibility(View.GONE);
                    }
                    mAdapter.notifyDataSetChanged();
                    hideLoading();
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }
}
