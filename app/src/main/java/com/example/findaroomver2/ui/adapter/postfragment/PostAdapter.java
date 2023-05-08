package com.example.findaroomver2.ui.adapter.postfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ItemPostHomeBinding;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.favourite.Favourite;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.comment.CommentListResponse;
import com.example.findaroomver2.response.favourite.CountFavourite;
import com.example.findaroomver2.response.favourite.FavouriteResponse;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.CommentActivity;
import com.example.findaroomver2.ui.activity.LoginActivity;
import com.example.findaroomver2.ui.bottomsheet.BottomSheetBookmark;
import com.example.findaroomver2.ui.bottomsheet.BottomSheetPersonFavourite;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> data;
    private NumberFormat fm = new DecimalFormat("#,###");
    private Repository repository;

    public PostAdapter(List<Post> data) {
        this.data = data;
        repository = new Repository();
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
        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);

        if (item != null) {
            repository.getUserById(item.getIdUser(), new Consumer<Data>() {
                @Override
                public void accept(Data data) {
                    holder.binding.nameUser.setText(data.getFullName());
                    Glide.with(holder.binding.imageUser.getContext()).load(data.getImage()).apply(optionsUser).into(holder.binding.imageUser);
                }
            });

            repository.getCountFavouriteByIdPost(item.get_id(), new Consumer<CountFavourite>() {
                @Override
                public void accept(CountFavourite countFavourite) {
                    holder.binding.heart.setText(countFavourite.getData().size() + "");
                }
            });

            repository.getListCommentParent(item.get_id(), new Consumer<CommentListResponse>() {
                @Override
                public void accept(CommentListResponse commentListResponse) {
                    holder.binding.commentCount.setText(commentListResponse.getData().size() + "");
                }
            });

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

            String token = MySharedPreferences.getInstance(holder.itemView.getContext()).getString(AppConstant.USER_TOKEN, "");
            repository.getFavouriteByIdUserAndIdPost(UserClient.getInstance().getId(), item.get_id(), new Consumer<FavouriteResponse>() {
                @Override
                public void accept(FavouriteResponse favouriteResponse) {
                    if (!token.equals("")) {
                        if (favouriteResponse.getFavourite() != null) {
                            holder.binding.btnHeart.setSelected(true);
                            int[] stateSet = {android.R.attr.state_checked * (holder.binding.btnHeart.isSelected() ? 1 : -1)};
                            holder.binding.btnHeart.setImageState(stateSet, true);
                        } else {
                            holder.binding.btnHeart.setSelected(false);
                            int[] stateSet = {android.R.attr.state_checked * (holder.binding.btnHeart.isSelected() ? 1 : -1)};
                            holder.binding.btnHeart.setImageState(stateSet, true);
                        }
                    }
                }
            });

            holder.binding.btnHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!token.equals("")) {
                        holder.binding.btnHeart.setSelected(!holder.binding.btnHeart.isSelected());
                        int[] stateSet = {android.R.attr.state_checked * (holder.binding.btnHeart.isSelected() ? 1 : -1)};
                        holder.binding.btnHeart.setImageState(stateSet, true);

                        if (!holder.binding.btnHeart.isSelected()) {
                            // xoá tim
                            holder.binding.heart.setText((Integer.parseInt(holder.binding.heart.getText().toString()) - 1) + "");
                            repository.deleteFavourite(UserClient.getInstance().getId(), item.get_id(), new Consumer<FavouriteResponse>() {
                                @Override
                                public void accept(FavouriteResponse favouriteResponse) {
                                }
                            });
                        } else {
                            // add tim
                            holder.binding.heart.setText((Integer.parseInt(holder.binding.heart.getText().toString()) + 1) + "");
                            repository.addFavourite(new Favourite(UserClient.getInstance().getId(), item.get_id()), new Consumer<FavouriteResponse>() {
                                @Override
                                public void accept(FavouriteResponse favouriteResponse) {
                                }
                            });
                        }
                    } else {
                        initDiaLog(holder.itemView.getContext());
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClickItem(item);
                }
            });

            holder.binding.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!token.equals("")) {
                        BottomSheetBookmark bottomsheetBookmark = new BottomSheetBookmark(view.getContext(), UserClient.getInstance().getId(), item.get_id(), repository);
                        bottomsheetBookmark.show();
                    } else {
                        initDiaLog(holder.itemView.getContext());
                    }
                }
            });

            holder.binding.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(holder.binding.heart.getText().toString()) > 0) {
                        BottomSheetPersonFavourite bottomSheetPersonFavourite = new BottomSheetPersonFavourite(view.getContext(), item.get_id(), repository);
                        bottomSheetPersonFavourite.show();
                    }
                }
            });

            holder.binding.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.binding.comment.getContext(), CommentActivity.class);
                    intent.putExtra(AppConstant.ID_POST, item.get_id());
                    holder.binding.comment.getContext().startActivity(intent);
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

    private void initDiaLog(Context context) {
        final Dialog dialogConfirmLogin = new Dialog(context);
        dialogConfirmLogin.setContentView(R.layout.dialog_comfirm_no_login);
        Window window2 = dialogConfirmLogin.getWindow();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogConfirmLogin != null && dialogConfirmLogin.getWindow() != null) {
            dialogConfirmLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialogConfirmLogin.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogConfirmLogin.dismiss();
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        });
        dialogConfirmLogin.show();
    }
}
