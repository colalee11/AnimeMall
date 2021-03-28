package com.dlsd.property.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlsd.property.R;

public class PhotoPopWindow extends PopupWindow {


    private TextView tvTitle;
    private LinearLayout llyCamera;
    private LinearLayout llyPhoto;
    private LinearLayout llyCancel;
    private OnConfirmClickListener listener;

    public PhotoPopWindow(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_photo, null);
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
        llyCamera = view.findViewById(R.id.lly_camera);
        llyPhoto = view.findViewById(R.id.lly_photo);
        llyCancel = view.findViewById(R.id.lly_cancel);

        view.findViewById(R.id.lly_camera).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCameraClicked();
                PhotoPopWindow.this.dismiss();
            }
        });

        view.findViewById(R.id.lly_photo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhotoClicked();
                PhotoPopWindow.this.dismiss();
            }
        });

        view.findViewById(R.id.lly_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPopWindow.this.dismiss();
            }
        });

    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.listener = listener;
    }

    public interface OnConfirmClickListener {
        void onPhotoClicked();

        void onCameraClicked();
    }
}
