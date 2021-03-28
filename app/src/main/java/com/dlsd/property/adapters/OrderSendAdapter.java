package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemOrderSendBinding;
import com.dlsd.property.db.Order;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Describe:
 */
public class OrderSendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Order> mDatas;
    private OnRecyclerItemClickListener listener;

    public OrderSendAdapter(Context context, ArrayList<Order> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOrderSendBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_order_send, parent, false);
        return new OrderSendAdapter.OrderHolder(binding);
    }

    /*
     *  0 待支付   1待收货  2已完成  3已取消   4已退货
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Order model = mDatas.get(position);
        OrderHolder vh = (OrderHolder) holder;
        OrderItemAdapter itemAdapter = new OrderItemAdapter(mContext, model.getGoodsList());
        itemAdapter.setOnItemClickListener(new OrderItemAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(OrderItemAdapter adapter, int childPosition) {
               
            }
        });
        vh.binding.rcvList.setLayoutManager(new LinearLayoutManager(mContext));
        vh.binding.rcvList.setAdapter(itemAdapter);
        vh.binding.tvOrderId.setText(model.getObjectId());
        vh.binding.tvPrice.setText("" + model.getTotleFee());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class OrderHolder extends RecyclerView.ViewHolder {

        ItemOrderSendBinding binding;

        private OrderHolder(ItemOrderSendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemConfirmClicked(OrderSendAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemConfirmClicked(OrderSendAdapter adapter, int position);//确认fa货
    }

}
