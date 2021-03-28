package com.dlsd.property.base;

import android.content.Context;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


public class BaseViewHolder extends RecyclerView.ViewHolder {

    protected Context mContext; // 上下文

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param itemView 布局View
     * @param binding 绑定
     */
    public BaseViewHolder(Context context, View itemView, final ViewDataBinding binding) {
        super(itemView);

    }
}
