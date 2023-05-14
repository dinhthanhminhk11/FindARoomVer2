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
import com.example.findaroomver2.databinding.ItemPersonFavouriteBinding;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.favourite.Favourite;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.ui.activity.DetailActivity;
import com.example.findaroomver2.ui.activity.ProfileActivity;

import java.util.List;
import java.util.function.Consumer;

public class PersonFavouriteAdapter extends RecyclerView.Adapter<PersonFavouriteAdapter.ViewHolder> {
    private List<Favourite> data;
    private Repository repository;

    public PersonFavouriteAdapter(List<Favourite> data, Repository repository) {
        this.data = data;
        this.repository = repository;
    }

    @NonNull
    @Override
    public PersonFavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPersonFavouriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonFavouriteAdapter.ViewHolder holder, int position) {
        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
        Favourite favourite = data.get(position);
        if (favourite != null) {
            repository.getUserById(favourite.getIdUser(), new Consumer<Data>() {
                @Override
                public void accept(Data data) {
                    holder.binding.nameUser.setText(data.getFullName());
                    Glide.with(holder.binding.imageUser.getContext()).load(data.getImage()).apply(optionsUser).into(holder.binding.imageUser);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    intent.putExtra(AppConstant.ID_USER, favourite.getIdUser());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPersonFavouriteBinding binding;

        public ViewHolder(@NonNull ItemPersonFavouriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
