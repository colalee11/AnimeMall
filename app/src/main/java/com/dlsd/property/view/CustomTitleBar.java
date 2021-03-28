package com.dlsd.property.view;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlsd.property.R;

public class CustomTitleBar extends RelativeLayout {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvMore;
    private ImageView ivMore;
    private Context mContext;

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context, attrs);
    }

    //初始化视图
    private void initView(final Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.header_common, this);
        ivBack = inflate.findViewById(R.id.iv_header_back);
        tvTitle = inflate.findViewById(R.id.tv_header_title);
        tvMore = inflate.findViewById(R.id.tv_header_more);
        ivMore = inflate.findViewById(R.id.iv_header_more);

        init(context, attributeSet);
    }

    //初始化资源文件
    public void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTitleBar);
        String title = typedArray.getString(R.styleable.CustomTitleBar_title);//标题
        int titleTextColor = typedArray.getResourceId(R.styleable.CustomTitleBar_title_text_color, R.color.txt_color_3);//左边图片
        int leftIcon = typedArray.getResourceId(R.styleable.CustomTitleBar_left_icon, R.drawable.btn_back);//左边图片
        int rightIcon = typedArray.getResourceId(R.styleable.CustomTitleBar_right_icon, R.drawable.ic_launcher);//右边图片
        String rightText = typedArray.getString(R.styleable.CustomTitleBar_right_text);//右边文字
        int titleBarType = typedArray.getInt(R.styleable.CustomTitleBar_titlebar_type, 10);//标题栏类型,默认为10
        boolean showBack = typedArray.getBoolean(R.styleable.CustomTitleBar_show_back, true);//是否显示返回


        //赋值进去我们的标题栏
        tvTitle.setText(title);
        tvTitle.setTextColor(getResources().getColor(titleTextColor));
        tvMore.setText(rightText);
        tvMore.setTextColor(getResources().getColor(titleTextColor));
        ivMore.setImageResource(rightIcon);

        //可以传入type值,可自定义判断值
        if (titleBarType == 10) {//不传入,默认为10,显示更多 文字,隐藏更多图标按钮
            ivMore.setVisibility(View.GONE);
            tvMore.setVisibility(View.VISIBLE);
        } else if (titleBarType == 11) {//传入11,显示更多图标按钮,隐藏更多 文字
            tvMore.setVisibility(View.GONE);
            ivMore.setVisibility(View.VISIBLE);
        } else if (titleBarType == 12) {//传入12,隐藏更多图标按钮,隐藏更多 文字
            tvMore.setVisibility(View.GONE);
            ivMore.setVisibility(View.GONE);
        }

        if(showBack){
            ivBack.setVisibility(VISIBLE);
            ivBack.setImageResource(leftIcon);
        }else {
            ivBack.setVisibility(INVISIBLE);
            ivBack.setClickable(false);
        }

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) mContext).onBackPressed();
            }
        });
    }

    //左边图片点击事件
    public void setLeftIconOnClickListener(OnClickListener l) {
        ivBack.setOnClickListener(l);
    }

    //右边图片点击事件
    public void setRightIconOnClickListener(OnClickListener l) {
        ivMore.setOnClickListener(l);
    }

    //右边文字点击事件
    public void setRightTextOnClickListener(OnClickListener l) {
        tvMore.setOnClickListener(l);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }
}
