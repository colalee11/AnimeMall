package com.dlsd.property.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dlsd.property.R;
import com.dlsd.property.databinding.ItemAddressBinding;
import com.dlsd.property.db.Address;

import java.util.ArrayList;

/**
 * Created by LSH
 * Describe:
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Address> mDatas;
    private OnRecyclerItemClickListener listener;

    public AddressAdapter(Context context, ArrayList<Address> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAddressBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_address, parent, false);
        return new AddressAdapter.AddressHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Address model = mDatas.get(position);
        AddressHolder vh = (AddressHolder) holder;
        vh.binding.tvName.setText(model.getName());
        vh.binding.tvPhone.setText(model.getPhone());
        vh.binding.tvAddress.setText("" + model.getAddress());
        if (model.isIsdefault()) {
            vh.binding.tvDefault.setVisibility(View.VISIBLE);
        } else {
            vh.binding.tvDefault.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class AddressHolder extends RecyclerView.ViewHolder {

        ItemAddressBinding binding;

        private AddressHolder(ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemEditClicked(AddressAdapter.this, getAdapterPosition());
                }
            });
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(AddressAdapter.this, getAdapterPosition());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClicked(AddressAdapter.this, getAdapterPosition());
                    return false;
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemEditClicked(AddressAdapter adapter, int position);

        void onItemClicked(AddressAdapter adapter, int position);

        void onItemLongClicked(AddressAdapter adapter, int position);
    }

}
