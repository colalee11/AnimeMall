package com.dlsd.property.guard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemGoodsTypeBinding;
import com.dlsd.property.db.GoodsType;

import java.util.ArrayList;

/**
 * Describe:
 */
public class GoodsTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<GoodsType> mDatas;
    private OnRecyclerItemClickListener listener;

    public GoodsTypeAdapter(Context context, ArrayList<GoodsType> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGoodsTypeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_goods_type, parent, false);
        return new GoodsTypeAdapter.GoodsTypeHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GoodsType model = mDatas.get(position);
        GoodsTypeHolder vh = (GoodsTypeHolder) holder;
        vh.binding.tvTypeId.setText(model.getTypeId());
        vh.binding.tvTypeName.setText(model.getTypeName());
        vh.binding.tvGoodsCount.setText("商品数量：" + model.getGoodsCount());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class GoodsTypeHolder extends RecyclerView.ViewHolder {

        ItemGoodsTypeBinding binding;

        private GoodsTypeHolder(ItemGoodsTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.tvGoodsCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemGoodsClicked(GoodsTypeAdapter.this, getAdapterPosition());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClicked(GoodsTypeAdapter.this, getAdapterPosition());
                    return false;
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemGoodsClicked(GoodsTypeAdapter adapter, int position);

        void onItemLongClicked(GoodsTypeAdapter adapter, int position);
    }

}
