package com.dlsd.property.view;

import androidx.viewpager.widget.ViewPager;

public interface PageIndicator  extends ViewPager.OnPageChangeListener {
    /**
     * Bind the indicator to a ViewPager.
     * 绑定指示器和viewpager
     * @param view
     */
    void setViewPager(ViewPager view);

    /**
     * Bind the indicator to a ViewPager.
     * 绑定指示器和viewpager
     * @param view
     * @param initialPosition
     */
    void setViewPager(ViewPager view, int initialPosition);

    /**
     * <p>Set the current page of both the ViewPager and indicator.</p>
     * <p/>
     * <p>This <strong>must</strong> be used if you need to set the page before
     * the views are drawn on screen (e.g., default start page).</p>
     * 跳到当前选项页面
     * @param item
     */
    void setCurrentItem(int item);

    /**
     * Set a page change listener which will receive forwarded events.
     * 设置监听器
     * @param listener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    /**
     * Notify the indicator that the fragment list has changed.
     * 更新
     */
    void notifyDataSetChanged();
}
