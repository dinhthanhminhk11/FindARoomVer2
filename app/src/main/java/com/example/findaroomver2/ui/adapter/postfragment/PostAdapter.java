package com.example.findaroomver2.ui.adapter.postfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ItemPostHomeBinding;
import com.example.findaroomver2.model.Post;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> data;
    private NumberFormat fm = new DecimalFormat("#,###");

    public PostAdapter(List<Post> data) {
        this.data = data;
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPostHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post item = data.get(position);

        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.noimage).error(R.drawable.noimage);

        if (item != null) {
            holder.binding.title.setText(item.getTitle());
            holder.binding.price.setText(fm.format(item.getPrice()) + " VND");
            holder.binding.content.setText(item.getDescribe());
            holder.binding.time.setText(item.getTime() + " " + item.getDate());
            if (item.getImages().size() == 1) {

                Glide.with(holder.binding.contentImage1Imag1.getContext()).load(item.getImages().get(0)).apply(options).into(holder.binding.contentImage1Imag1);
                holder.binding.contentImage1.setVisibility(View.VISIBLE);
                holder.binding.contentImage2.setVisibility(View.GONE);
                holder.binding.contentImage3.setVisibility(View.GONE);
                holder.binding.contentImage4.setVisibility(View.GONE);
            } else if (item.getImages().size() == 2) {
                Glide.with(holder.binding.contentImage2Image1.getContext()).load(item.getImages().get(0)).apply(options).into(holder.binding.contentImage2Image1);
                Glide.with(holder.binding.contentImage2Image2.getContext()).load(item.getImages().get(1)).apply(options).into(holder.binding.contentImage2Image2);

                holder.binding.contentImage1.setVisibility(View.GONE);
                holder.binding.contentImage2.setVisibility(View.VISIBLE);
                holder.binding.contentImage3.setVisibility(View.GONE);
                holder.binding.contentImage4.setVisibility(View.GONE);
            } else if (item.getImages().size() == 3) {
                Glide.with(holder.binding.contentImage3Image1.getContext()).load(item.getImages().get(0)).apply(options).into(holder.binding.contentImage3Image1);
                Glide.with(holder.binding.contentImage3Image2.getContext()).load(item.getImages().get(1)).apply(options).into(holder.binding.contentImage3Image2);
                Glide.with(holder.binding.contentImage3Image3.getContext()).load(item.getImages().get(2)).apply(options).into(holder.binding.contentImage3Image3);

                holder.binding.contentImage1.setVisibility(View.GONE);
                holder.binding.contentImage2.setVisibility(View.GONE);
                holder.binding.contentImage3.setVisibility(View.VISIBLE);
                holder.binding.contentImage4.setVisibility(View.GONE);
            } else {

                Glide.with(holder.binding.contentImage4Image1.getContext()).load(item.getImages().get(0)).apply(options).into(holder.binding.contentImage4Image1);
                Glide.with(holder.binding.contentImage4Image2.getContext()).load(item.getImages().get(1)).apply(options).into(holder.binding.contentImage4Image2);
                Glide.with(holder.binding.contentImage4Image3.getContext()).load(item.getImages().get(2)).apply(options).into(holder.binding.contentImage4Image3);
                Glide.with(holder.binding.contentImage4Image4.getContext()).load(item.getImages().get(3)).apply(options).into(holder.binding.contentImage4Image4);

                if (item.getImages().size() > 4) {
                    holder.binding.countImage.setVisibility(View.VISIBLE);
                    holder.binding.countImage.setText("+" + (item.getImages().size() - 4));
                    holder.binding.checkImage4.setBackgroundResource(R.drawable.gradient_bg);
                } else {
                    holder.binding.countImage.setVisibility(View.GONE);
                    holder.binding.checkImage4.setBackground(null);
                }

                holder.binding.contentImage1.setVisibility(View.GONE);
                holder.binding.contentImage2.setVisibility(View.GONE);
                holder.binding.contentImage3.setVisibility(View.GONE);
                holder.binding.contentImage4.setVisibility(View.VISIBLE);
            }
            holder.binding.btnHeart.setImageResource(R.drawable.ic_heart_item_post_animation_no_select);


            // đoạn này check xem người dùng đã tim bài viết này chưa
            int i = 1;
            if (i == 2) {
                holder.binding.btnHeart.setSelected(true);
                int[] stateSet = {android.R.attr.state_checked * (holder.binding.btnHeart.isSelected() ? 1 : -1)};
                holder.binding.btnHeart.setImageState(stateSet, true);
            } else {
                holder.binding.btnHeart.setSelected(false);
                int[] stateSet = {android.R.attr.state_checked * (holder.binding.btnHeart.isSelected() ? 1 : -1)};
                holder.binding.btnHeart.setImageState(stateSet, true);
            }

            holder.binding.btnHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.binding.btnHeart.setSelected(!holder.binding.btnHeart.isSelected());
                    int[] stateSet = {android.R.attr.state_checked * (holder.binding.btnHeart.isSelected() ? 1 : -1)};
                    holder.binding.btnHeart.setImageState(stateSet, true);

                    if (!holder.binding.btnHeart.isSelected()) {
                        // xoá tim
                        Toast.makeText(holder.binding.btnHeart.getContext(), "Tawts", Toast.LENGTH_SHORT).show();
                    } else {
                        // add tim
                        Toast.makeText(holder.binding.btnHeart.getContext(), "bawjt", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClickItem(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostHomeBinding binding;

        public ViewHolder(ItemPostHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onClickItem(Post post);

        void onClickMore(Post post);

        void onClickAddHeart(Post post);

        void onClickRemoteHeart(Post post);
    }
}
