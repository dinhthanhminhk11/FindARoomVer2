package com.example.findaroomver2.ui.adapter.postfragment;

import android.net.Uri;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_1 = 1;
    private final int VIEW_TYPE_2 = 2;
    private final int VIEW_TYPE_3 = 3;
    private final int VIEW_TYPE_4 = 4;

    private List<Uri> datas;

    public PostAdapter(List<Uri> imagePaths) {
        this.datas = imagePaths;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void changePath(List<Uri> imagePaths) {
        this.datas = imagePaths;
        notifyDataSetChanged();
    }


}
