package com.example.findaroomver2.ui.adapter.homefragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;

import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.MyViewHolder> {
    private List<String> image;
    private EventClick eventClick;

    public GalleryImageAdapter(List<String> image, EventClick eventClick) {
        this.image = image;
        this.eventClick = eventClick;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_second, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_first, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage);
        Glide.with(holder.itemView.getContext())
                .load(image.get(position))
                .apply(options)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(v -> {
            eventClick.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) return 2;
        else return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img1);
        }
    }
    public interface EventClick {
        void onClick(int id);
    }
}
