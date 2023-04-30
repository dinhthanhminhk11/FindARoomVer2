package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityDetailGalleryBinding;
import com.example.findaroomver2.ui.adapter.homefragment.DetailGalleryAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailGalleryActivity extends AppCompatActivity implements DetailGalleryAdapter.EventClick {
    private ActivityDetailGalleryBinding binding;
    private List<String> listImage = new ArrayList<>();
    private int position;
    private boolean clicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        listImage = (ArrayList<String>) bundle.getSerializable("LIST_IMAGE");
        position = bundle.getInt("POSITION");
        binding.imgCancelGallery.setOnClickListener(v -> onBackPressed());
        binding.viewPageGallery.setAdapter(new DetailGalleryAdapter(listImage, this));
        binding.viewPageGallery.setClipToPadding(true);
        binding.viewPageGallery.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        binding.viewPageGallery.setCurrentItem(position);
        binding.tvCountGallery.setText("/ " + listImage.size());
        binding.tvNumberChange.setText(position + 1 + "");

        binding.viewPageGallery.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tvNumberChange.setText(position + 1 + "");
            }
        });
    }

    @Override
    public void onClick() {
        if (clicked) {
            binding.constraintDetailImage.setBackgroundResource(R.color.black);
            binding.imgCancelGallery.setVisibility(View.GONE);
            binding.viewPageGallery.setUserInputEnabled(false);
            binding.view.setVisibility(View.GONE);
            clicked = false;
        } else {
            binding.constraintDetailImage.setBackgroundResource(R.color.white);
            binding.imgCancelGallery.setVisibility(View.VISIBLE);
            binding.view.setVisibility(View.VISIBLE);
            clicked = true;
            binding.viewPageGallery.setUserInputEnabled(true);
        }
    }
}