package com.dlsd.property.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlsd.property.R;
import com.dlsd.property.models.Experience;

import java.util.ArrayList;

/**
 * Created by LSH on 2018/11/6.
 */

public class FilterExperienceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Experience> mDatas;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private int code = -1;

    public FilterExperienceAdapter(Context context, ArrayList<Experience> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_filter, parent, false);
        return new FilterExperienceAdapter.FilterHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Experience model = mDatas.get(position);
        FilterHolder viewHolder = (FilterHolder) holder;
        viewHolder.tv_filter.setText(model.getName());
        if (position == code) {
            viewHolder.tv_filter.setTextColor(mContext.getResources().getColor(R.color.theme_txt_color));
            viewHolder.tv_filter.setBackgroundResource(R.drawable.bg_filter_select);
        } else {
            viewHolder.tv_filter.setTextColor(mContext.getResources().getColor(R.color.txt_color_3));
            viewHolder.tv_filter.setBackgroundResource(R.drawable.bg_filter);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class FilterHolder extends RecyclerView.ViewHolder {

        TextView tv_filter;

        private FilterHolder(View itemView) {
            super(itemView);
            tv_filter = (TextView) itemView.findViewById(R.id.tv_filter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(FilterExperienceAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(FilterExperienceAdapter adapter, int position);
    }

    public void setCode(int code) {
        if (this.code == code) {
            this.code = -1;
        } else {
            this.code = code;
        }
    }

    public int getCode() {
        return code;
    }
}
