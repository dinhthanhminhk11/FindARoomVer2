package com.example.findaroomver2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityForgotBinding;
import com.example.findaroomver2.request.changepass.Email;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.viewmodel.ForgotPassViewModel;

public class ForgotActivity extends AppCompatActivity {

    private ActivityForgotBinding binding;

    private ForgotPassViewModel forgotPassViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        forgotPassViewModel = new ViewModelProvider(this).get(ForgotPassViewModel.class);
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotActivity.this, RegisterActivity.class));
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.username.getText().toString().isEmpty()) {
                    Toast.makeText(ForgotActivity.this, "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
                } else {
                    forgotPassViewModel.sendMailForgotPass(new Email(binding.username.getText().toString()));
                }
            }
        });

        forgotPassViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        forgotPassViewModel.getTextResponseMutableLiveData().observe(this, new Observer<TextResponse>() {
            @Override
            public void onChanged(TextResponse textResponse) {
                if (textResponse.isStatus()) {
                    Intent intent = new Intent(ForgotActivity.this, OTPPasswordActivity.class);
                    intent.putExtra(AppConstant.EMAIL_USER, binding.username.getText().toString().trim());
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgotActivity.this, textResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}