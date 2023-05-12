package com.example.findaroomver2.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.TimeUtils;
import com.example.findaroomver2.databinding.ItemLayoutNotificationBinding;
import com.example.findaroomver2.model.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationModel> data;
    private Callback callback;
    private int backgroundNotSeem = R.drawable.background_not_seem;
    private int backgroundSeem = R.drawable.background_seem;

    public void setData(List<NotificationModel> data) {
        this.data = data;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setColor( int backgroundNotSeem, int backgroundSeem) {
        this.backgroundNotSeem = backgroundNotSeem;
        this.backgroundSeem = backgroundSeem;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLayoutNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        NotificationModel notification = data.get(position);
        if (notification != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.noavatar)
                    .error(R.drawable.noavatar);
            Glide.with(holder.itemView.getContext()).load(notification.getImagePost()).apply(options).into(holder.binding.image);
            holder.binding.content.setText(notification.getContent());
            holder.binding.dateAndTime.setText(TimeUtils.getTimeAgo(notification.getTimeLong()));
            holder.binding.title.setText(notification.getTitle());
            holder.itemView.setOnClickListener(v -> {
                callback.onClick(notification);
            });

            if (notification.isSeem()) {
                holder.itemView.setBackgroundResource(backgroundNotSeem);
            } else {
                holder.itemView.setBackgroundResource(backgroundSeem);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLayoutNotificationBinding binding;

        public ViewHolder(ItemLayoutNotificationBinding itemLayoutNotificationBinding) {
            super(itemLayoutNotificationBinding.getRoot());
            this.binding = itemLayoutNotificationBinding;
        }
    }

    public interface Callback {
        void onClick(NotificationModel notification);
    }
}
