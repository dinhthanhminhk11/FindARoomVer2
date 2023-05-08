package com.example.findaroomver2.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ItemPostManagerBinding;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.ui.activity.DetailActivity;
import com.example.findaroomver2.ui.bottomsheet.BottomSheetManagerPost;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class ManagerPostAdapter extends RecyclerView.Adapter<ManagerPostAdapter.ViewHolder> {
    private List<Post> data;
    private NumberFormat fm = new DecimalFormat("#,###");
    private Repository repository;

    public ManagerPostAdapter(List<Post> data) {
        this.data = data;
        repository = new Repository();
    }

    @NonNull
    @Override
    public ManagerPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPostManagerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerPostAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.noimage).error(R.drawable.noimage);
        Post post = data.get(position);
        if (post != null) {
            Glide.with(holder.binding.imagePost.getContext()).load(post.getImages().get(0)).apply(options).into(holder.binding.imagePost);
            holder.binding.title.setText(post.getTitle());
            holder.binding.content.setText(post.getDescribe());
            holder.binding.price.setText(fm.format(post.getPrice()) + " VND");
            holder.binding.statusConfrim.setText(post.getMessageConfirm());
            if (post.isStatusConfirm()) {
                holder.binding.statusConfrim.setTextColor(Color.GREEN);
            } else {
                holder.binding.statusConfrim.setTextColor(Color.RED);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra(AppConstant.ID_POST, post.get_id());
                    holder.itemView.getContext().startActivity(intent);
                }
            });

            holder.binding.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomSheetManagerPost bottomSheetManagerPost = new BottomSheetManagerPost(holder.itemView.getContext());
                    bottomSheetManagerPost.setCallback(new BottomSheetManagerPost.Callback() {
                        @Override
                        public void onClickDelete() {
                            repository.deletePost(post.get_id(), new Consumer<PostResponse>() {
                                @Override
                                public void accept(PostResponse postResponse) {
                                    if (postResponse.getMessage().isStatus()) {
                                        Toast.makeText(holder.itemView.getContext(), postResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                                        data.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onClickEdit() {

                        }
                    });
                    bottomSheetManagerPost.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostManagerBinding binding;

        public ViewHolder(ItemPostManagerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
