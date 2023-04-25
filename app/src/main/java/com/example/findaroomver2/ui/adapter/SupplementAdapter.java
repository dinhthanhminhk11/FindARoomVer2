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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SupplementAdapter(List<Supplement> data) {
        this.data = data;
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

//            holder.binding.checkboxRent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    if (b) {
//                        holder.binding.checkboxRent.setChecked(b);
//                        selectedItemsSupplement.add(supplement);
//                    } else {
//                        selectedItemsSupplement.remove(supplement);
//                    }
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItemSelected(selectedItemsSupplement);
//                    }
//                    notifyDataSetChanged();
//                }
//            });
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
