package com.example.findaroomver2.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findaroomver2.databinding.ItemSupplementBinding;
import com.example.findaroomver2.response.supplement.Supplement;

import java.util.ArrayList;
import java.util.List;

public class SupplementAdapter extends RecyclerView.Adapter<SupplementAdapter.ViewHolder> {
    private List<Supplement> selectedItemsSupplement = new ArrayList<>();
    private List<Supplement> data;

    private OnItemClickListener onItemClickListener;

    private List<Supplement> dataChecker;

    public void setDataChecker(List<Supplement> dataChecker) {
        this.dataChecker = dataChecker;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<Supplement> data) {
        this.data = data;
    }

    public SupplementAdapter() {
    }

    @NonNull
    @Override
    public SupplementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSupplementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SupplementAdapter.ViewHolder holder, int position) {
        Supplement supplement = data.get(position);
        if (supplement instanceof Supplement) {
            holder.binding.checkboxRent.setText(supplement.getName());

            holder.binding.checkboxRent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.binding.checkboxRent.isChecked()) {
                        selectedItemsSupplement.add(supplement);
                    } else {
                        selectedItemsSupplement.remove(supplement);
                    }

                    if (onItemClickListener != null) {
                        onItemClickListener.onItemSelected(selectedItemsSupplement);
                    }
                }
            });
            if (dataChecker != null) {
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < dataChecker.size(); j++) {
                        if (data.get(i).getId().equals(dataChecker.get(j).getId())) {
                            selectedItemsSupplement.add(supplement);
                            holder.binding.checkboxRent.setChecked(true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemSupplementBinding binding;

        public ViewHolder(ItemSupplementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemSelected(List<Supplement> selectedItems);
    }
}
