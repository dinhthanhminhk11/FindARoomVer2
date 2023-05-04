package com.example.findaroomver2.ui.adapter.homefragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.TimeUtils;
import com.example.findaroomver2.databinding.ItemCommentDetailBinding;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.ui.adapter.comment.CommentAdapter;

import java.util.List;
import java.util.function.Consumer;

public class CommentDetailAdapter extends RecyclerView.Adapter<CommentDetailAdapter.ViewHolder> {

    private Repository repository;
    private CommentAdapter.Callback callback;
    private List<Comment> data;

    public CommentDetailAdapter(List<Comment> data) {
        this.data = data;
        repository = new Repository();
    }

    @NonNull
    @Override
    public CommentDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCommentDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentDetailAdapter.ViewHolder holder, int position) {
        Comment comment = data.get(position);
        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
        if (comment != null) {
            repository.getUserById(comment.getIdUser(), new Consumer<Data>() {
                @Override
                public void accept(Data data) {
                    holder.binding.tvNameUser.setText(data.getFullName());
                    Glide.with(holder.binding.imgUser.getContext()).load(data.getImage()).apply(optionsUser).into(holder.binding.imgUser);


                }
            });
            holder.binding.tvContent.setText(comment.getContent());
            holder.binding.tvTime.setText(TimeUtils.getTimeAgo(Long.parseLong(comment.getTimeLong())));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCommentDetailBinding binding;

        public ViewHolder(ItemCommentDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
