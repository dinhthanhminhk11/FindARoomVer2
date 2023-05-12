package com.example.findaroomver2.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findaroomver2.databinding.ItemCashFlowBinding;
import com.example.findaroomver2.response.money.CashFlowResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CashFlowAdapter extends RecyclerView.Adapter<CashFlowAdapter.ViewHolder> {
    private List<CashFlowResponse> data;
    private NumberFormat fm = new DecimalFormat("#,###");

    public CashFlowAdapter() {
    }

    public void setData(List<CashFlowResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CashFlowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCashFlowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CashFlowAdapter.ViewHolder holder, int position) {
        CashFlowResponse CashFlowResponse = data.get(position);
        if (CashFlowResponse != null) {
            holder.binding.title.setText(CashFlowResponse.getTitle());
            holder.binding.content.setText(CashFlowResponse.getContent());
            holder.binding.dateAndTime.setText(CashFlowResponse.getDataTime());
            if (CashFlowResponse.isStatus()) {
                holder.binding.price.setTextColor(Color.GREEN);
                holder.binding.price.setText("+" + fm.format(Integer.parseInt(CashFlowResponse.getPrice())));
            } else {
                if(Integer.parseInt(CashFlowResponse.getPrice())==0){
                    holder.binding.price.setVisibility(View.GONE);
                }else {
                    holder.binding.price.setVisibility(View.VISIBLE);
                    holder.binding.price.setText("-" + fm.format(Integer.parseInt(CashFlowResponse.getPrice())));
                    holder.binding.price.setTextColor(Color.RED);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCashFlowBinding binding;

        public ViewHolder(ItemCashFlowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
