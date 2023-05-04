package com.example.findaroomver2.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.request.login.Data;

import java.util.Date;
import java.util.List;

public class HostAdapter extends RecyclerView.Adapter<HostAdapter.MyViewHolder> {
    private List<Data> listHost;
    private EventClick eventClick;

    private int color = Color.BLACK;
    private int color2;
    private int background;

    public HostAdapter() {
    }

    public void setListHost(List<Data> listHost) {
        this.listHost = listHost;
    }

    public void setEventClick(EventClick eventClick) {
        this.eventClick = eventClick;
    }

    @NonNull
    @Override
    public HostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_host_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HostAdapter.MyViewHolder holder, int position) {
        Data userLogin = listHost.get(position);
        if (userLogin != null) {
            RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
            Glide.with(holder.itemView.getContext()).load(userLogin.getImage()).apply(optionsUser).into(holder.imgHost);

            holder.nameHost.setText(userLogin.getFullName());
            holder.itemView.setOnClickListener(v -> eventClick.onClick(userLogin));
        }

    }

    @Override
    public int getItemCount() {
        return listHost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHost;
        private TextView nameHost;
        private TextView contentText;
        private LinearLayout contentBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHost = itemView.findViewById(R.id.imgHost);
            nameHost = itemView.findViewById(R.id.tvNameHost);
            contentText = itemView.findViewById(R.id.contentText);
            contentBackground = (LinearLayout) itemView.findViewById(R.id.contentBackground);
        }
    }

    public interface EventClick {
        void onClick(Data user);
    }
}
