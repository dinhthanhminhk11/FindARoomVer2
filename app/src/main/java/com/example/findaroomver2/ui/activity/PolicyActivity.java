package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityPolicyBinding;

public class PolicyActivity extends AppCompatActivity {

    private ActivityPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolBar.setTitle("");
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}