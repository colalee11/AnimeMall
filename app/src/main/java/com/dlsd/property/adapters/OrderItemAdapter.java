package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemOrderItemBinding;
import com.dlsd.property.db.OrderGoods;

import java.util.ArrayList;


/**
 *
 * Describe:
 */
public class OrderItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<OrderGoods> mDatas;
    private OnRecyclerItemClickListener listener;

    public OrderItemAdapter(Context context, ArrayList<OrderGoods> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOrderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_order_item, parent, false);
        return new OrderItemAdapter.OrderItemHolder(binding);
    }

    /*
     *  0 待支付   1待收货  2已完成
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OrderGoods model = mDatas.get(position);
        OrderItemHolder vh = (OrderItemHolder) holder;
        vh.binding.tvGoodName.setText(model.getGoods().getGoodsName());
        vh.binding.tvGoodIntroduction.setText(model.getGoods().getGoodsIntroduction());
        vh.binding.tvGoodsPrice.setText("¥ " + model.getGoods().getGoodsPrice());
        vh.binding.tvGoodsCount.setText("" + model.getCount());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class OrderItemHolder extends RecyclerView.ViewHolder {

        ItemOrderItemBinding binding;

        private OrderItemHolder(ItemOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(OrderItemAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClicked(OrderItemAdapter adapter, int position);
    }

}
