package com.dlsd.property.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlsd.property.R;

import static android.view.View.VISIBLE;

public class PayPopWindow extends PopupWindow {


    private TextView tvTitle;
    private TextView tvNum;
    private TextView tvNumSub;
    private ImageView ivAli;
    private ImageView ivWx;
    private String contractTag;
    private OnConfirmClickListener listener;

    public PayPopWindow(Context context, String title, String payNum, String contractTag, boolean ali, boolean wx) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_pay, null);
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwindow_anim_style);
        // 实例化一个ColorDrawable颜色为全透明
        ColorDrawable dw = new ColorDrawable(-00000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        tvTitle = view.findViewById(R.id.tv_title);
        tvNum = view.findViewById(R.id.tv_num);
        tvNumSub = view.findViewById(R.id.tv_num_sub);
        ivAli = view.findViewById(R.id.iv_ali);
        ivWx = view.findViewById(R.id.iv_wx);

        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(title);
        }

        if (TextUtils.isEmpty(payNum)) {
            tvNum.setText("");
            tvNumSub.setText("");
        } else {
            if (!TextUtils.isEmpty(contractTag) && contractTag.equals("0")) {
                tvNum.setText("0.00");
            } else {
                tvNum.setText(payNum);
            }
            tvNumSub.setText(payNum);
        }

        if (ali) {
            view.findViewById(R.id.lly_ali_pay).setVisibility(VISIBLE);
            ivAli.setVisibility(VISIBLE);
        }
        if (wx) {
            view.findViewById(R.id.lly_wx_pay).setVisibility(VISIBLE);
            if (view.findViewById(R.id.lly_ali_pay).getVisibility() == VISIBLE) {
                ivWx.setVisibility(View.GONE);
            } else {
                ivWx.setVisibility(VISIBLE);
            }
        }

        view.findViewById(R.id.lly_ali_pay).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ivAli.setVisibility(VISIBLE);
                ivWx.setVisibility(View.GONE);
            }
        });

        view.findViewById(R.id.lly_wx_pay).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ivAli.setVisibility(View.GONE);
                ivWx.setVisibility(VISIBLE);
            }
        });

        view.findViewById(R.id.btn_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivAli.getVisibility() == VISIBLE) {
                    listener.onConfirmClicked(context, "ALIPAY");
                } else {
                    listener.onConfirmClicked(context, "WXPAY");
                }
                PayPopWindow.this.dismiss();
            }
        });

        view.findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPopWindow.this.dismiss();
            }
        });
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.listener = listener;
    }

    public interface OnConfirmClickListener {
        void onConfirmClicked(Context context, String payType);
    }
}
