package com.dlsd.property.merchants.activitys;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dlsd.property.R;
import com.dlsd.property.base.AppManager;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityGuardMainBinding;
import com.dlsd.property.fragments.MineFragment;
import com.dlsd.property.fragments.MerchantsHomeFragment;

import java.util.ArrayList;
import java.util.List;

import static com.dlsd.property.utils.StatusBarUtil.setRootViewFitsSystemWindows;
import static com.dlsd.property.utils.StatusBarUtil.setStatusBarDarkTheme;

/**
 * 首页
 */
public class GuardMainActivity extends BaseActivity {

    private ActivityGuardMainBinding binding;

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guard_main);
    }

    @Override
    protected void initView() {
        setRootViewFitsSystemWindows(this, false);
        setStatusBarDarkTheme(this, false);
        initViewPager();
    }

    @Override
    protected void initData() {

    }

    private void initViewPager() {
        // init fragment
        mFragments.add(MerchantsHomeFragment.newInstance());
        mFragments.add(MineFragment.newInstance());
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        binding.fragmentVp.setAdapter(mAdapter);
        // register listener
        binding.fragmentVp.addOnPageChangeListener(mPageChangeListener);
        binding.tabGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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

    private void initMenuTextColor(int position) {
        switch (position) {
            case 0:
                binding.oneTab.setTextColor(getResources().getColor(R.color.theme_txt_color));
                binding.twoTab.setTextColor(getResources().getColor(R.color.txt_base_sub));
                break;
            case 1:
                binding.oneTab.setTextColor(getResources().getColor(R.color.txt_base_sub));
                binding.twoTab.setTextColor(getResources().getColor(R.color.theme_txt_color));
                break;
        }
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
                showToast("再按一次退出程序");
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
}
