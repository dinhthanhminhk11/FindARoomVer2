package com.example.findaroomver2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.constant.NotificationCenter;
import com.example.findaroomver2.databinding.ActivityLoginBinding;
import com.example.findaroomver2.event.KeyEvent;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.LoginViewModel;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.username.setText("dinhthanhminhk11@gmail.com");
        binding.password.setText("12345678d");
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(v -> {
            if (binding.username.getText().toString().isEmpty()) {
                CustomToast.ct(this, "Bạn chưa nhập email hoặc số điện thoại", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
            } else if (binding.password.getText().toString().isEmpty()) {
                CustomToast.ct(this, "Bạn chưa nhập mật khẩu", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();

            } else if (!isPhoneNumberOrGmail(binding.username.getText().toString())) {
                CustomToast.ct(this, "Nhập sai định dạng gmail hoặc số điện thoại", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
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
                if (userResponseLogin.getMessage().isStatus()) {

                    MySharedPreferences.getInstance(LoginActivity.this).putString(AppConstant.USER_TOKEN, userResponseLogin.getData().getAccessToken());

                    UserClient userClient = UserClient.getInstance();
                    userClient.setEmail(userResponseLogin.getData().getEmail());
                    userClient.setPhone(userResponseLogin.getData().getPhone());
                    userClient.setFullName(userResponseLogin.getData().getFullName());
                    userClient.setId(userResponseLogin.getData().getId());
                    EventBus.getDefault().postSticky(new KeyEvent(NotificationCenter.checkLogin));
                    finish();
                } else {
                    CustomToast.ct(LoginActivity.this, userResponseLogin.getMessage().getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
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

    public static boolean isPhoneNumberOrGmail(String input) {
        String phoneRegex = "^(03|05|07|08|09)+([0-9]{8})\\b";
        String gmailRegex = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

        return input.matches(phoneRegex) || input.matches(gmailRegex);
    }
}