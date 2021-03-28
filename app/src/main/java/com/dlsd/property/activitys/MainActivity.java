package com.dlsd.property.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dlsd.property.R;
import com.dlsd.property.base.AppManager;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.constant.Constants;
import com.dlsd.property.databinding.ActivityMainBinding;
import com.dlsd.property.fragments.MainFragment;
import com.dlsd.property.fragments.MineFragment;
import com.dlsd.property.fragments.ShopBusFragment;

import java.util.ArrayList;
import java.util.List;

import static com.dlsd.property.utils.StatusBarUtil.setRootViewFitsSystemWindows;
import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码


    public static boolean isForeground = false;
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    private ShopBusFragment shopBusFragment;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initView() {
        setRootViewFitsSystemWindows(this, false);
        setStatusBarDarkTheme(this, false);
    }

    private void initViewPager() {
        // init fragment
        shopBusFragment = new ShopBusFragment();
        mFragments = new ArrayList<>(3);
        mFragments.add(new MainFragment());
        mFragments.add(shopBusFragment);
        mFragments.add(new MineFragment());
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        binding.fragmentVp.setAdapter(mAdapter);
        // register listener
        binding.fragmentVp.addOnPageChangeListener(mPageChangeListener);
        binding.tabGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    protected void initData() {
        initViewPager();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.fragmentVp.removeOnPageChangeListener(mPageChangeListener);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.e("ljh", "onRestart");
        int registeredResult = getIntent().getIntExtra(Constants.REGISTERED_RESULT, 0);
        Log.e("ljh", "registeredResult = " + registeredResult);
        Log.e("ljh", "binding.fragmentVp.getChildCount() = " + binding.fragmentVp.getChildCount());
        if (registeredResult == registeredResult && binding.fragmentVp.getChildCount() >= 2) {
            binding.fragmentVp.setCurrentItem(registeredResult);
            initMenuTextColor(registeredResult);
        }
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(position==1 && null != shopBusFragment){
                shopBusFragment.setRefresh();
            }
        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) binding.tabGroup.getChildAt(position);
            radioButton.setChecked(true);
            initMenuTextColor(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    binding.fragmentVp.setCurrentItem(i);
                    initMenuTextColor(i);
                    return;
                }
            }
        }
    };

    public void setClinic(int index) {
        binding.fragmentVp.setCurrentItem(1);
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                AppManager.getAppManager().finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initMenuTextColor(int position) {
        switch (position) {
            case 0:
                binding.oneTab.setTextColor(getResources().getColor(R.color.theme_txt_color));
                binding.twoTab.setTextColor(getResources().getColor(R.color.txt_base_sub));
                binding.threeTab.setTextColor(getResources().getColor(R.color.txt_base_sub));
                break;
            case 1:
                binding.oneTab.setTextColor(getResources().getColor(R.color.txt_base_sub));
                binding.twoTab.setTextColor(getResources().getColor(R.color.theme_txt_color));
                binding.threeTab.setTextColor(getResources().getColor(R.color.txt_base_sub));
                break;
            case 2:
                binding.oneTab.setTextColor(getResources().getColor(R.color.txt_base_sub));
                binding.twoTab.setTextColor(getResources().getColor(R.color.txt_base_sub));
                binding.threeTab.setTextColor(getResources().getColor(R.color.theme_txt_color));
                break;
        }
    }


    /**
     * 获取动态权限
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0才用动态权限
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECORD_AUDIO};
            mPermissionList.clear();//清空没有通过的权限
            //逐个判断你要的权限是否已经通过
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);//添加还未授予的权限
                }
            }
            //申请权限
            if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
                ActivityCompat.requestPermissions(this, permissions, mRequestCode);
            } else {
                //权限都已经通过
                //UpdateHint();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                finish();//不让他继续访问
            } else {
                //全部权限通过
                //UpdateHint();
            }
        }
    }

}
