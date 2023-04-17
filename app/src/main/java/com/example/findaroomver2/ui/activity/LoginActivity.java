package com.example.findaroomver2.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.databinding.ActivityLoginBinding;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.username.setText("0987655431");
        binding.password.setText("1234567d");
        binding.btnLogin.setOnClickListener(v -> {
            loginViewModel.login(new UserLoginRequest(binding.username.getText().toString(), binding.password.getText().toString()));
        });

        loginViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });

        loginViewModel.getUserResponseLoginMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                if(userResponseLogin.getMessage().getStatus().equals("success")){
                    Toast.makeText(LoginActivity.this, "Login thanh công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Login thật bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}