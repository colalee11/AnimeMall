package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemMainTypeBinding;
import com.dlsd.property.db.GoodsType;

import java.util.ArrayList;

/**
 *
 */

public class MainTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<GoodsType> mDatas;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public MainTypeAdapter(Context context, ArrayList<GoodsType> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainTypeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_type, parent, false);
        return new MainTypeAdapter.MainTypeHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsType model = mDatas.get(position);
        MainTypeHolder vh = (MainTypeHolder) holder;
        vh.binding.tvTitle.setText(model.getTypeName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class MainTypeHolder extends RecyclerView.ViewHolder {

        ItemMainTypeBinding binding;

        private MainTypeHolder(ItemMainTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(MainTypeAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(MainTypeAdapter adapter, int position);
    }
}
