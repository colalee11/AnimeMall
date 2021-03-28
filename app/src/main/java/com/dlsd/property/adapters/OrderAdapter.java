package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemOrderBinding;
import com.dlsd.property.db.Order;

import java.util.ArrayList;


/**
 * Describe:
 */
public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Order> mDatas;
    private OnRecyclerItemClickListener listener;

    public OrderAdapter(Context context, ArrayList<Order> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_order, parent, false);
        return new OrderAdapter.OrderHolder(binding);
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
                listener.onItemDetailClicked(position, childPosition);
            }
        });
        vh.binding.rcvList.setLayoutManager(new LinearLayoutManager(mContext));
        vh.binding.rcvList.setAdapter(itemAdapter);
        vh.binding.tvOrderId.setText(model.getObjectId());
        vh.binding.tvPrice.setText("" + model.getTotleFee());
        switch (model.getState()) {
            case 0:
                vh.binding.tvOrderState.setText("待支付");
                vh.binding.llyBtn.setVisibility(View.VISIBLE);
                vh.binding.btnPay.setVisibility(View.VISIBLE);
                vh.binding.btnRefuse.setVisibility(View.VISIBLE);
                vh.binding.btnConfirm.setVisibility(View.GONE);
                break;
            case 1:
                vh.binding.tvOrderState.setText("待发货");
                vh.binding.llyBtn.setVisibility(View.GONE);
                vh.binding.btnPay.setVisibility(View.GONE);
                vh.binding.btnRefuse.setVisibility(View.GONE);
                vh.binding.btnConfirm.setVisibility(View.GONE);
                break;
            case 2:
                vh.binding.tvOrderState.setText("待收货");
                vh.binding.llyBtn.setVisibility(View.VISIBLE);
                vh.binding.btnPay.setVisibility(View.GONE);
                vh.binding.btnRefuse.setVisibility(View.VISIBLE);
                vh.binding.btnConfirm.setVisibility(View.VISIBLE);
                break;
            case 3:
                vh.binding.tvOrderState.setText("已完成");
                vh.binding.llyBtn.setVisibility(View.GONE);
                vh.binding.btnPay.setVisibility(View.GONE);
                vh.binding.btnRefuse.setVisibility(View.GONE);
                vh.binding.btnConfirm.setVisibility(View.GONE);
                break;
            case 4:
                vh.binding.tvOrderState.setText("已取消");
                vh.binding.llyBtn.setVisibility(View.GONE);
                vh.binding.btnPay.setVisibility(View.GONE);
                vh.binding.btnRefuse.setVisibility(View.GONE);
                vh.binding.btnConfirm.setVisibility(View.GONE);
                break;
            case 5:
                vh.binding.tvOrderState.setText("已退货");
                vh.binding.llyBtn.setVisibility(View.GONE);
                vh.binding.btnPay.setVisibility(View.GONE);
                vh.binding.btnRefuse.setVisibility(View.GONE);
                vh.binding.btnConfirm.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class OrderHolder extends RecyclerView.ViewHolder {

        ItemOrderBinding binding;

        private OrderHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            binding.btnRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemRefuseClicked(OrderAdapter.this, getAdapterPosition());
                }
            });
            binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemConfirmClicked(OrderAdapter.this, getAdapterPosition());
                }
            });
            binding.btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemPayClicked(OrderAdapter.this, getAdapterPosition());
                }
            });
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(OrderAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {

        void onItemClicked(OrderAdapter adapter, int position);

        void onItemDetailClicked(int groupPosition, int childPosition);

        void onItemPayClicked(OrderAdapter adapter, int position);//立即支付

        void onItemRefuseClicked(OrderAdapter adapter, int position);//取消订单

        void onItemConfirmClicked(OrderAdapter adapter, int position);//确认收货
    }

}
