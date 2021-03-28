package com.dlsd.property.activitys.base;

import android.content.Intent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dlsd.property.R;
import com.dlsd.property.activitys.MainActivity;
import com.dlsd.property.base.BaseActivity;
import com.dlsd.property.databinding.ActivityGuideBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;

public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private ActivityGuideBinding binding;

    //导航页图片资源
    public int[] guides = new int[]{R.drawable.zw,
            R.drawable.zw, R.drawable.zw, R.drawable.zw};

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guide);
    }

    @Override
    protected void initView() {
        List<View> mList = new ArrayList<View>();
        LayoutInflater inflat = LayoutInflater.from(this);
        //先添加一个最左侧空的view
        View item = inflat.inflate(R.layout.page_guide, null);
        mList.add(item);
        for (int index : guides) {
            item = inflat.inflate(R.layout.page_guide, null);
            item.setBackgroundResource(index);
            mList.add(item);
        }
        //经过遍历，此时item是最后一个view，设置button
        Button btn = (Button) item.findViewById(R.id.button1);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(this);//设置最后一个页面上button的监听
        //再添加一个最右侧空的view
        item = inflat.inflate(R.layout.page_guide, null);
        mList.add(item);
        //ViewPager最重要的设置Adapter，这和ListView一样的原理
        MViewPageAdapter adapter = new MViewPageAdapter(mList);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(adapter);
        binding.viewPager.setCurrentItem(1);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                toMain();
                break;
        }
    }

    /**
     * 内部类，继承PagerAdapter，当然你也可以直接 new PageAdapter
     *
     * @author yangxiaolong
     */
    class MViewPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private List<View> mViewList;

        public MViewPageAdapter(List<View> views) {
            mViewList = views;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position), 0);
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                binding.viewPager.setCurrentItem(1);
            } else if (position == mViewList.size() - 1) {
                binding.viewPager.setCurrentItem(position - 1);
                Toast.makeText(getApplicationContext(), "页面即将跳转",
                        Toast.LENGTH_SHORT).show();
                //应该在这里跳转到MainActivity
                toMain();
            }
        }
    }

    private void toMain() {
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
    }
}
