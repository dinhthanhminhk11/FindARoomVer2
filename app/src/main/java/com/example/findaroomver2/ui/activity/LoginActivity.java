package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.findaroomver2.MainActivity;
import com.example.findaroomver2.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout txtUsername, txtPassword;
    Button btnSignIn;
    TextView tvDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.btn_Login);
        tvDangKy = findViewById(R.id.tv_SignUp);

        final  Intent intentMain = new Intent(this, MainActivity.class);
        final  Intent intentRegister = new Intent(this, RegisterActivity.class);

        //ấn đăng nhập chuyển vào màn hình chính
        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(intentMain);
            }
        });

        //ấn vào màn hình đăng ký tài khoản
        tvDangKy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(intentRegister);
            }
        });
   }
}