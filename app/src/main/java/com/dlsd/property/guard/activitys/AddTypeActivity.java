package com.dlsd.property.guard.activitys;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dlsd.property.R;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityAddTypeBinding;
import com.dlsd.property.db.GoodsType;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class AddTypeActivity extends BaseActivity {

    private ActivityAddTypeBinding binding;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_type);
    }

    @Override
    protected void initView() {
        setStatusBarDarkTheme(this, false);
        binding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.etTypeName.getText().toString().trim())) {
                    showToast("请填写分类名称");
                    return;
                }
                if (TextUtils.isEmpty(binding.etTypeId.getText().toString().trim()) || binding.etTypeId.getText().toString().trim().length() != 6) {
                    showToast("请填写6位分类编号");
                    return;
                }
                BmobQuery<GoodsType> query = new BmobQuery<GoodsType>();
                query.addWhereEqualTo("typeId", binding.etTypeId.getText().toString().trim());
                query.findObjects(new FindListener<GoodsType>() {
                    @Override
                    public void done(List<GoodsType> object, BmobException e) {
                        if (e == null) {
                            if (object.size() > 0) {
                                showToast("分类编号已存在");
                            } else {
                                GoodsType gt = new GoodsType();
                                gt.setTypeName(binding.etTypeName.getText().toString().trim());
                                gt.setTypeId(binding.etTypeId.getText().toString().trim());
                                gt.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (null == e) {
                                            showToast("保存成功");
                                            finish();
                                        } else {
                                            showToast(getString(R.string.error_msg));
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
        });
    }

    @Override
    protected void initData() {

    }
}
