package com.example.findaroomver2.ui.adapter.homefragment;

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
import com.example.findaroomver2.databinding.ItemPostTrendBinding;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.ViewUpdatePost;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.DetailActivity;
import com.example.findaroomver2.ui.bottomsheet.BottomSheetBookmark;

import java.util.List;
import java.util.function.Consumer;

public class PostTrendAdapter extends RecyclerView.Adapter<PostTrendAdapter.ViewHolder> {
    private List<Post> data;
    private Repository repository;

    public PostTrendAdapter(List<Post> data) {
        this.data = data;
        repository = new Repository();
    }

    @NonNull
    @Override
    public PostTrendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPostTrendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostTrendAdapter.ViewHolder holder, int position) {
        Post post = data.get(position);
        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
        if (post != null) {
            repository.getUserById(post.getIdUser(), new Consumer<Data>() {
                @Override
                public void accept(Data data) {
                    Glide.with(holder.binding.imageUser.getContext()).load(data.getImage()).apply(optionsUser).into(holder.binding.imageUser);
                }
            });
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.noimage);
            Glide.with(holder.itemView.getContext()).load(post.getImages().get(0)).apply(options).into(holder.binding.image);

            String token = MySharedPreferences.getInstance(holder.itemView.getContext()).getString(AppConstant.USER_TOKEN, "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!token.equals("")) {
                        repository.updateView(new ViewUpdatePost(UserClient.getInstance().getId(), post.get_id()));
                    }
                    Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra(AppConstant.ID_POST, post.get_id());
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

        private ItemPostTrendBinding binding;

        public ViewHolder(ItemPostTrendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
