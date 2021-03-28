package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemGoodsItemBusBinding;
import com.dlsd.property.db.BusGoods;

import java.util.ArrayList;

/**
 *
 * Describe:
 */
public class GoodItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<BusGoods> mDatas;
    private OnRecyclerItemClickListener listener;

    public GoodItemsAdapter(Context context, ArrayList<BusGoods> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGoodsItemBusBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_goods_item_bus, parent, false);
        return new GoodItemsAdapter.GoodItemsHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BusGoods model = mDatas.get(position);
        GoodItemsHolder vh = (GoodItemsHolder) holder;
        vh.binding.tvTitle.setText(model.getGoods().getGoodsName());
        vh.binding.tvSubTitle.setText(model.getGoods().getGoodsIntroduction());
        vh.binding.tvPrice.setText("¥" + model.getGoods().getGoodsPrice());
        vh.binding.tvCount.setText("" + model.getCount());
        if (model.isCheck()) {
            vh.binding.ivCheck.setImageResource(R.drawable.new_on_choice);
        } else {
            vh.binding.ivCheck.setImageResource(R.drawable.new_off_choice);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class GoodItemsHolder extends RecyclerView.ViewHolder {

        ItemGoodsItemBusBinding binding;

        private GoodItemsHolder(ItemGoodsItemBusBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.ivCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemCheckClicked(GoodItemsAdapter.this, getAdapterPosition());
                }
            });
            binding.tvSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSubClicked(GoodItemsAdapter.this, getAdapterPosition());
                }
            });
            binding.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemAddClicked(GoodItemsAdapter.this, getAdapterPosition());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClicked(GoodItemsAdapter.this, getAdapterPosition());
                    return false;
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemCheckClicked(GoodItemsAdapter adapter, int position);

        void onItemAddClicked(GoodItemsAdapter adapter, int position);

        void onItemSubClicked(GoodItemsAdapter adapter, int position);

        void onItemLongClicked(GoodItemsAdapter adapter, int position);
    }

}
