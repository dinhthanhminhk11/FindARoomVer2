package com.example.findaroomver2.ui.adapter.autoimage;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.event.ImageController;
import com.example.findaroomver2.ui.customview.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageAutoSliderAdapter extends SliderViewAdapter<ImageAutoSliderAdapter.Holder> {
    private Context context;
    private List<Uri> imagePaths;

    public ImageAutoSliderAdapter(Context context, List<Uri> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item, parent, false);
        return new ImageAutoSliderAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Uri imagePath = imagePaths.get(position);
        holder.imageView.setImageURI(imagePath);
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    public class Holder extends ViewHolder {
        private ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_view);

        }
    }

    public void changePath(List<Uri> imagePaths) {
        this.imagePaths = imagePaths;
        notifyDataSetChanged();
    }
}

