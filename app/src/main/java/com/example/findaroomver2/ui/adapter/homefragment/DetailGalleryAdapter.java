package com.example.findaroomver2.ui.adapter.homefragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;

import java.util.List;

public class DetailGalleryAdapter extends RecyclerView.Adapter<DetailGalleryAdapter.MyViewHolder>{
    private List<String> list;
    private EventClick  eventClick;

    public DetailGalleryAdapter(List<String> list, EventClick eventClick) {
        this.list = list;
        this.eventClick = eventClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_gallery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage);
        Glide.with(holder.itemView.getContext())
                .load(list.get(position))
                .transform(new CenterCrop(),new RoundedCorners(30))
                .apply(options)
                .into(holder.img);
        holder.itemView.setOnClickListener(v -> eventClick.onClick());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgDetailGallery);
        }
    }

    public interface EventClick {
        void onClick();
    }
}
