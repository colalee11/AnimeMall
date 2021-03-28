package com.dlsd.property.view;

import android.app.Service;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dlsd.property.R;

public class SelfEditPopWindow extends PopupWindow {


    private TextView tvTitle;
    private EditText et_content;
    private TextView tv_confirm;
    private OnConfirmClickListener listener;
    private Context mContext;

    public SelfEditPopWindow(Context context, String title, String hint) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_self_edit, null);
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
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        et_content = view.findViewById(R.id.et_content);
        if (!TextUtils.isEmpty(hint)) {
            et_content.setHint(hint);
        }
        tv_confirm = view.findViewById(R.id.tv_confirm);
        et_content.setFocusable(true);
        et_content.setFocusableInTouchMode(true);
        et_content.setSelection(et_content.getText().toString().length());//将光标移至文字末尾
        view.findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_content.getText().toString().trim())) {
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onConfirmClicked(context, et_content.getText().toString().trim());
                SelfEditPopWindow.this.dismiss();
            }
        });


        view.findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SelfEditPopWindow.this.dismiss();
            }
        });
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.listener = listener;
    }

    public interface OnConfirmClickListener {
        void onConfirmClicked(Context context, String content);
    }

    public EditText getEt_content() {
        return et_content;
    }
    public void setInput() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_content, 0);
    }
}
