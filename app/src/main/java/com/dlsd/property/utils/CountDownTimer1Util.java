package com.dlsd.property.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class CountDownTimer1Util extends CountDownTimer {
    private TextView mTextView;
    private OnFinishedListener listener;

    public CountDownTimer1Util(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText("剩余(" + millisUntilFinished / 1000 + ")");  //设置倒计时时间
    }

    @Override
    public void onFinish() {
        if (null != listener)
            listener.onFinish();
    }

    public void setOnFinishedListener(OnFinishedListener listener) {
        this.listener = listener;
    }

    public interface OnFinishedListener {
        void onFinish();
    }
}
