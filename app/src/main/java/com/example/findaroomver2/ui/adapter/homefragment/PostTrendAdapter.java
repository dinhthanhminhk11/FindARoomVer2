package com.example.findaroomver2.ui.adapter.homefragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ItemPostTrendBinding;

import java.util.List;

public class PostTrendAdapter extends RecyclerView.Adapter<PostTrendAdapter.ViewHolder> {
    private List<String> data;

    public PostTrendAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PostTrendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPostTrendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostTrendAdapter.ViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage);
        Glide.with(holder.itemView.getContext()).load(data.get(position)).apply(options).into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemPostTrendBinding binding;

        public ViewHolder(ItemPostTrendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
