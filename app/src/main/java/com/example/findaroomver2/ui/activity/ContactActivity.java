package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityContactBinding;

public class ContactActivity extends AppCompatActivity {

    private ActivityContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolBar.setTitle("Về FindARoom");
        setSupportActionBar(binding.toolBar);
        binding.   toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }
}