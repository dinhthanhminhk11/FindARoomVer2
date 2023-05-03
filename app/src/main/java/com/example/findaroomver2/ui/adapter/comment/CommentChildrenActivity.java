package com.example.findaroomver2.ui.adapter.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.TimeUtils;
import com.example.findaroomver2.databinding.ItemCommentParentBinding;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.request.login.Data;

import java.util.List;
import java.util.function.Consumer;

public class CommentChildrenActivity extends RecyclerView.Adapter<CommentChildrenActivity.ViewHolder> {

    private Repository repository;
    private List<Comment> data;

    public CommentChildrenActivity(List<Comment> data) {
        this.data = data;
        repository = new Repository();
    }

    @NonNull
    @Override
    public CommentChildrenActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentChildrenActivity.ViewHolder(ItemCommentParentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentChildrenActivity.ViewHolder holder, int position) {
        Comment comment = data.get(position);
        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
        if (comment != null) {
            holder.itemCommentParentBinding.content.setText(comment.getContent());
            holder.itemCommentParentBinding.reply.setVisibility(View.GONE);
            repository.getUserById(comment.getIdUser(), new Consumer<Data>() {
                @Override
                public void accept(Data data) {
                    holder.itemCommentParentBinding.nameUser.setText(data.getFullName());
                    Glide.with(holder.itemCommentParentBinding.imageUser.getContext()).load(data.getImage()).apply(optionsUser).into(holder.itemCommentParentBinding.imageUser);
                }
            });
            holder.itemCommentParentBinding.time.setText(TimeUtils.getTimeAgo(Long.parseLong(comment.getTimeLong())));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCommentParentBinding itemCommentParentBinding;

        public ViewHolder(ItemCommentParentBinding itemCommentParentBinding) {
            super(itemCommentParentBinding.getRoot());
            this.itemCommentParentBinding = itemCommentParentBinding;
        }
    }
}
