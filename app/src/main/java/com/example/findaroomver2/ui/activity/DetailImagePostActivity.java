package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityDetailImagePostBinding;
import com.example.findaroomver2.ui.adapter.homefragment.GalleryImageAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailImagePostActivity extends AppCompatActivity implements GalleryImageAdapter.EventClick {

    private ActivityDetailImagePostBinding binding;
    private List<String> img = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailImagePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        img = (List<String>) bundle.getSerializable(AppConstant.IMAGE_GALLERY);
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolBar.setTitle("Ảnh");
        binding.toolBar.setPadding(30, 0, 0, 0);
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 3 == 0) return 2;
                else return 1;
            }
        });
        binding.rcvImageGallery.setLayoutManager(gridLayoutManager);
        binding.rcvImageGallery.setAdapter(new GalleryImageAdapter(img, this));
    }

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(this, DetailGalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.POSITION, id);
        bundle.putSerializable(AppConstant.LIST_IMAGE, (Serializable) img);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}