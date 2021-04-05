package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemGoodsBusBinding;
import com.dlsd.property.db.BusGoods;

import java.util.ArrayList;

/**
 * Describe:购物车适配器
 */
public class GoodGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<BusGoods> mDatas;
    private OnRecyclerItemClickListener listener;

    public GoodGroupAdapter(Context context, ArrayList<BusGoods> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGoodsBusBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_goods_bus, parent, false);
        return new GoodGroupAdapter.GoodGroupHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //BusGoods model = mDatas.get(position);
        GoodGroupHolder vh = (GoodGroupHolder) holder;
        GoodItemsAdapter itemsAdapter = new GoodItemsAdapter(mContext, mDatas);
        itemsAdapter.setOnItemClickListener(new GoodItemsAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemCheckClicked(GoodItemsAdapter adapter, int position) {
                listener.onItemCheckClicked(position);
            }

            @Override
            public void onItemAddClicked(GoodItemsAdapter adapter, int position) {
                listener.onItemAddClicked(position);
            }

            @Override
            public void onItemSubClicked(GoodItemsAdapter adapter, int position) {
                listener.onItemSubClicked(position);
            }

            @Override
            public void onItemLongClicked(GoodItemsAdapter adapter, int pos) {
                listener.onItemLongClicked(position, pos);
            }
        });
        vh.binding.rcvList.setLayoutManager(new LinearLayoutManager(mContext));
        vh.binding.rcvList.setAdapter(itemsAdapter);
    }

    @Override
    public int getItemCount() {
        return /*mDatas.size()*/1;
    }

    private class GoodGroupHolder extends RecyclerView.ViewHolder {

        ItemGoodsBusBinding binding;

        private GoodGroupHolder(ItemGoodsBusBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.llyDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemDetailClicked(GoodGroupAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemDetailClicked(GoodGroupAdapter adapter, int position);

        void onItemCheckClicked(int position);

        void onItemLongClicked(int gPostion, int position);

        void onItemAddClicked(int position);

        void onItemSubClicked(int position);
    }

}
