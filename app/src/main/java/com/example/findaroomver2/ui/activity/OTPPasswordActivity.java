package com.example.findaroomver2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findaroomver2.databinding.ActivityOtppasswordBinding;

public class OTPPasswordActivity extends AppCompatActivity {

    private ActivityOtppasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtppasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OTPPasswordActivity.this, NewPasswordActivity.class));
            }
        });

        binding.sendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}