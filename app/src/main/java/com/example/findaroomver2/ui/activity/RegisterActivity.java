package com.example.findaroomver2.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.MainActivity;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityRegisterBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.register.UserRegisterRequest;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private ImageView close;
    private Button login;
    private TextView btnCancel;
    private Button loginDialog;
    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding binding;
    private int role = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerViewModel.register(new UserRegisterRequest(binding.sodienthoai.getText().toString(), binding.diachiemai.getText().toString(), binding.password02.getText().toString(), binding.username.getText().toString(), role, "token"));
            }
        });

        binding.checkboxFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkboxFind.isChecked()) {
                    binding.checkboxRent.setChecked(false);
                    role = 0;
                } else {
                    binding.checkboxFind.setChecked(true);
                }
            }
        });
        binding.checkboxRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkboxRent.isChecked()) {
                    binding.checkboxFind.setChecked(false);
                    role = 2;
                } else {
                    binding.checkboxRent.setChecked(true);
                }
            }
        });

        registerViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        registerViewModel.getUserResponseLoginMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                if (userResponseLogin.getMessage().getStatus().equals("success")) {
                    confirmLogin(binding.diachiemai.getText().toString(), binding.password02.getText().toString());
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerViewModel.getUserResponseLoginAgainMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                if (userResponseLogin.getMessage().getStatus().equals("success")) {

                    MySharedPreferences.getInstance(RegisterActivity.this).putString(AppConstant.USER_TOKEN, userResponseLogin.getData().getAccessToken());
                    UserClient userClient = UserClient.getInstance();
                    userClient.setEmail(userResponseLogin.getData().getEmail());
                    userClient.setPhone(userResponseLogin.getData().getPhone());
                    userClient.setFullName(userResponseLogin.getData().getFullName());
                    userClient.setId(userResponseLogin.getData().getId());

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void confirmLogin(String username, String password) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_comfirm_login);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        close = (ImageView) dialog.findViewById(R.id.close);
        btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        loginDialog = (Button) dialog.findViewById(R.id.login);

        loginDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewModel.login(new UserLoginRequest(username, password));
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }
}