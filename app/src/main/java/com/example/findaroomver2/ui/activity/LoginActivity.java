package com.example.findaroomver2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.MainActivity;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityLoginBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.username.setText("minhdtph13562@gmail.com");
        binding.password.setText("12345678d");
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(v -> {
            if (binding.username.getText().toString().isEmpty()) {
                Toast.makeText(this, "Bạn chưa nhập email hoặc số điện thoại", Toast.LENGTH_SHORT).show();
            } else if (binding.password.getText().toString().isEmpty()) {
                Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                loginViewModel.login(new UserLoginRequest(binding.username.getText().toString(), binding.password.getText().toString()));
            }
        });

        loginViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        loginViewModel.getUserResponseLoginMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                if (userResponseLogin.getMessage().getStatus().equals("success")) {

                    MySharedPreferences.getInstance(LoginActivity.this).putString(AppConstant.USER_TOKEN, userResponseLogin.getData().getAccessToken());
                    UserClient userClient = UserClient.getInstance();
                    userClient.setEmail(userResponseLogin.getData().getEmail());
                    userClient.setPhone(userResponseLogin.getData().getPhone());
                    userClient.setFullName(userResponseLogin.getData().getFullName());
                    userClient.setId(userResponseLogin.getData().getId());

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login thật bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}