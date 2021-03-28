package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemGoodsItemConfirmBinding;
import com.dlsd.property.db.OrderGoods;

import java.util.ArrayList;

/**
 * Describe:
 */
public class GoodItemsConfirmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<OrderGoods> mDatas;
    private OnRecyclerItemClickListener listener;

    public GoodItemsConfirmAdapter(Context context, ArrayList<OrderGoods> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGoodsItemConfirmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_goods_item_confirm, parent, false);
        return new GoodItemsConfirmAdapter.GoodItemsConfirmHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderGoods model = mDatas.get(position);
        GoodItemsConfirmHolder vh = (GoodItemsConfirmHolder) holder;
        vh.binding.tvTitle.setText(model.getGoods().getGoodsName());
        vh.binding.tvSubTitle.setText(model.getGoods().getGoodsIntroduction());
        vh.binding.tvPrice.setText("¥" + model.getGoods().getGoodsPrice());
        vh.binding.tvCount.setText("" + model.getCount());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class GoodItemsConfirmHolder extends RecyclerView.ViewHolder {

        ItemGoodsItemConfirmBinding binding;

        private GoodItemsConfirmHolder(ItemGoodsItemConfirmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {

    }

}
