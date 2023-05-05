package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityPayCashYourBinding;

public class PayCashYourActivity extends AppCompatActivity {
    private ActivityPayCashYourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayCashYourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initToolbar();
    }

    private void initView() {
        binding.contentAddMoney.setOnClickListener(v -> {
            startActivity(new Intent(this, AddMoneyActivity.class));
        });
    }

    private void initToolbar() {
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolBar.setTitle("Ví của bạn");
        binding.toolBar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }
}