package com.dlsd.property.view.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlsd.property.R;

import java.util.ArrayList;

public class PopSubListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;
    private OnRecyclerItemClickListener listener;
    private int mSelectPosition = -1;
    private String mSelected;
    public PopSubListAdapter(Context context, ArrayList<String> datas, String selected) {
        this.mContext = context;
        this.mDatas = datas;
        this.mSelected = selected;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_pop_sub_list, parent, false);
        return new PopSubListAdapter.PopSubListHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PopSubListHolder viewHolder = (PopSubListHolder) holder;
        viewHolder.tv_filter.setText(mDatas.get(position));
        if (mSelectPosition == position || (null != mSelected && mSelected.equals(mDatas.get(position)))) {
            viewHolder.tv_filter.setBackgroundResource(R.drawable.bg_filter_pop_select);
            viewHolder.tv_filter.setTextColor(mContext.getResources().getColor(R.color.theme_txt_color));
        } else {
            viewHolder.tv_filter.setBackgroundResource(R.drawable.bg_filter_pop);
            viewHolder.tv_filter.setTextColor(mContext.getResources().getColor(R.color.txt_color_3));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class PopSubListHolder extends RecyclerView.ViewHolder {

        TextView tv_filter;

        private PopSubListHolder(View itemView) {
            super(itemView);
            tv_filter = (TextView) itemView.findViewById(R.id.tv_filter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(PopSubListAdapter.this, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClicked(PopSubListAdapter adapter, int position);
    }

    public void setmSelectPosition(int mSelectPosition) {
        this.mSelectPosition = mSelectPosition;
        notifyDataSetChanged();
    }
}
