package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemMenuBinding;
import com.dlsd.property.models.MenuItem;

import java.lang.reflect.Field;
import java.util.ArrayList;



public class MainMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<MenuItem> mDatas;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public MainMenuAdapter(Context context, ArrayList<MenuItem> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMenuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_menu, parent, false);
        return new MainMenuAdapter.MainMenuHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MenuItem model = mDatas.get(position);
        MainMenuHolder vh = (MainMenuHolder) holder;
        vh.binding.tvTitle.setText(model.getTitle());
        vh.binding.ivMenu.setImageResource(getResourceId(model.getIconId()));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class MainMenuHolder extends RecyclerView.ViewHolder {

        ItemMenuBinding binding;

        private MainMenuHolder(ItemMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(MainMenuAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(MainMenuAdapter adapter, int position);
    }

    public int getResourceId(String name) {
        try {
            // 根据图片资源的文件名获得Field对象
            Field field = R.drawable.class.getField(name);
            // 取得并返回资源ID
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
        }
        return 0;
    }
}
