package com.example.findaroomver2.event;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageController {
    private ImageView imgMain;

    public ImageController(ImageView imgMain) {
        this.imgMain = imgMain;
    }

    public void setImgMain(Uri path) {
        Glide.with(imgMain.getContext())
                .load(path)
                .into(imgMain);
    }
}
