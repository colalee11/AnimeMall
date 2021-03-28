package com.dlsd.property.activitys.address;

import android.app.Activity;
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

public class AddressListActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAddressListBinding binding;


    private AddressAdapter mAdapter;
    private ArrayList<Address> mDatas;
    private boolean haveSelect = false;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_list);
    }

    @Override
    protected void initView() {
        mDatas = new ArrayList<Address>();
        if (null == getIntent().getSerializableExtra("address")) {
            haveSelect = false;
        } else {
            haveSelect = true;
        }
        mAdapter = new AddressAdapter(AddressListActivity.this, mDatas);
        mAdapter.setOnItemClickListener(new AddressAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemEditClicked(AddressAdapter adapter, int position) {
                Intent intent = new Intent(AddressListActivity.this, EditAddressActivity.class);
                intent.putExtra("model", mDatas.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClicked(AddressAdapter adapter, int position) {
                Intent intent = new Intent();
                intent.putExtra("model", mDatas.get(position));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onItemLongClicked(AddressAdapter adapter, int position) {

            }
        });
        binding.rcvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvList.setAdapter(mAdapter);

        binding.titlebar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressListActivity.this, AddAddressActivity.class));
            }
        });

    }

    @Override
    protected void initData() {

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

    /**
     * 收药地址
     */
    private void getAddressList() {
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
                } else {
                    showToast(getString(R.string.error_msg));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (haveSelect) {
            super.onBackPressed();
        } else {
            for (Address bean : mDatas) {
                if (bean.isIsdefault()) {
                    Intent intent = new Intent();
                    intent.putExtra("model", bean);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
            super.onBackPressed();
        }

    }
}
