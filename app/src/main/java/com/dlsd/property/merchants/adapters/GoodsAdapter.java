package com.dlsd.property.merchants.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemGoodsBinding;
import com.dlsd.property.db.Goods;

import java.util.ArrayList;

/**
 * Describe:
 */
public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Goods> mDatas;
    private OnRecyclerItemClickListener listener;

    public GoodsAdapter(Context context, ArrayList<Goods> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGoodsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_goods, parent, false);
        return new GoodsAdapter.GoodsHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Goods model = mDatas.get(position);
        GoodsHolder vh = (GoodsHolder) holder;
        vh.binding.tvGoodName.setText(model.getGoodsName());
        vh.binding.tvGoodIntroduction.setText(model.getGoodsIntroduction());
        vh.binding.tvSeeCount.setText("浏览: " + model.getGoodsSeeCount());
        vh.binding.tvGoodsPrice.setText("¥" + model.getGoodsPrice());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class GoodsHolder extends RecyclerView.ViewHolder {

        ItemGoodsBinding binding;

        private GoodsHolder(ItemGoodsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(GoodsAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClicked(GoodsAdapter adapter, int position);
    }

}
