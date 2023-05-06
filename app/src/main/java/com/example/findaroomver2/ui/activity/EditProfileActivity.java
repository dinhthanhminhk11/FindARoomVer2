package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolBar();
        intView();
        initData();
    }

    private void initToolBar() {
        binding.toolBar.setTitle("Chỉnh sửa thông tin");
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void intView() {

    }

    private void initData() {

    }
}