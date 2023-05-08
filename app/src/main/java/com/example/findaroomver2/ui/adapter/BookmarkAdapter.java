package com.example.findaroomver2.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ItemBookmarkBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.bookmark.Bookmark;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.ui.activity.DetailActivity;
import com.example.findaroomver2.ui.bottomsheet.BottomSheetBookmark;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private NumberFormat fm = new DecimalFormat("#,###");
    private Repository repository;
    private List<Bookmark> data;

    public BookmarkAdapter(List<Bookmark> data) {
        this.data = data;
        repository = new Repository();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBookmarkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Bookmark bookmark = data.get(position);
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.noimage).error(R.drawable.noimage);
        if (bookmark != null) {
            repository.getPostById(bookmark.getIdPost(), new Consumer<PostResponse>() {
                @Override
                public void accept(PostResponse postResponse) {
                    Glide.with(holder.binding.imagePost.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(holder.binding.imagePost);
                    holder.binding.title.setText(postResponse.getData().getTitle());
                    holder.binding.content.setText(postResponse.getData().getDescribe());
                    holder.binding.price.setText(fm.format(postResponse.getData().getPrice()) + " VND");
                }
            });

            holder.binding.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomSheetBookmark bottomsheetBookmark = new BottomSheetBookmark(view.getContext(), UserClient.getInstance().getId(), bookmark.getIdPost(), repository);
                    bottomsheetBookmark.setCallback(new BottomSheetBookmark.Callback() {
                        @Override
                        public void onClick() {
                            data.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    bottomsheetBookmark.show();
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra(AppConstant.ID_POST, bookmark.getIdPost());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBookmarkBinding binding;

        public ViewHolder(ItemBookmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
