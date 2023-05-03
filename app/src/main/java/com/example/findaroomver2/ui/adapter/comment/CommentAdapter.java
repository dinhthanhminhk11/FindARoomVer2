package com.example.findaroomver2.ui.adapter.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.TimeUtils;
import com.example.findaroomver2.databinding.ItemCommentParentBinding;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.comment.CommentListResponse;

import java.util.List;
import java.util.function.Consumer;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Repository repository;
    private Callback callback;
    private List<Comment> data;
    private String name = "";

    public CommentAdapter(List<Comment> data) {
        this.data = data;
        repository = new Repository();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCommentParentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = data.get(position);
        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
        if (comment != null) {
            repository.getUserById(comment.getIdUser(), new Consumer<Data>() {
                @Override
                public void accept(Data data) {
                    name = data.getFullName();
                    holder.itemCommentParentBinding.nameUser.setText(data.getFullName());
                    Glide.with(holder.itemCommentParentBinding.imageUser.getContext()).load(data.getImage()).apply(optionsUser).into(holder.itemCommentParentBinding.imageUser);


                }
            });
            holder.itemCommentParentBinding.listChildrenComment.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false));

            if (comment.getParentCommentId() == null) {
                repository.getListCommentChildren(comment.get_id(), new Consumer<CommentListResponse>() {
                    @Override
                    public void accept(CommentListResponse commentListResponse) {
                        CommentChildrenActivity commentChildrenActivity = new CommentChildrenActivity(commentListResponse.getData());
                        holder.itemCommentParentBinding.listChildrenComment.setAdapter(commentChildrenActivity);
                    }
                });
            }

            holder.itemCommentParentBinding.content.setText(comment.getContent());

            holder.itemCommentParentBinding.reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onCLickReply(comment.get_id(), name);
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

    public interface Callback {
        void onCLickReply(String idComment, String nameUser);
    }
}
