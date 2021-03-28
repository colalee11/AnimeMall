package zhangphil.iosdialog.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by LSH on 2018/8/23.
 */

public class MyScrollView extends ScrollView {
    private Context mContext;

    //回调监听接口
    private OnScrollChangeListener mOnScrollChangeListener;
    //标识是否滑动到顶部
    private boolean isScrollToStart = false;
    //标识是否滑动到底部
    private boolean isScrollToEnd = false;
    private static final int CODE_TO_START = 0x001;
    private static final int CODE_TO_END = 0x002;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_TO_START:
                    //重置标志“滑动到顶部”时的标志位
                    isScrollToStart = false;
                    break;
                case CODE_TO_END:
                    //重置标志“滑动到底部”时的标志位
                    isScrollToEnd = false;
                    break;
                default:
                    break;
            }
        }
    };

    public MyScrollView(Context context) {
        super(context);
        init(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            //最大高度显示为屏幕内容高度的一半
            Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
            DisplayMetrics d = new DisplayMetrics();
            display.getMetrics(d);
            //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(d.heightPixels / 2, MeasureSpec.AT_MOST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangeListener != null) {
            Log.i("CustomScrollView", "scrollY:" + getScrollY());
            //滚动到顶部，ScrollView存在回弹效果效应（这里只会调用两次，如果用<=0,会多次触发）
            if (getScrollY() == 0) {
                //过滤操作，优化为一次调用
                if (!isScrollToStart) {
                    isScrollToStart = true;
                    mHandler.sendEmptyMessageDelayed(CODE_TO_START, 200);
                    mOnScrollChangeListener.onScrollToStart();
                }
            } else {
                View contentView = getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() == (getScrollY() + getHeight())) {
                    //滚动到底部，ScrollView存在回弹效果效应
                    //优化，只过滤第一次
                    if (!isScrollToEnd) {
                        isScrollToEnd = true;
                        mHandler.sendEmptyMessageDelayed(CODE_TO_END, 200);
                        Log.e("CustomScrollView", "toEnd,scrollY:" + getScrollY());
                        mOnScrollChangeListener.onScrollToEnd();
                    }
                }
            }
        }

    }

    //滑动监听接口
    public interface OnScrollChangeListener {

        //滑动到顶部时的回调
        void onScrollToStart();

        //滑动到底部时的回调
        void onScrollToEnd();
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }
}
