package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.findaroomver2.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnDangKy;
    TextView tvDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnDangKy = findViewById(R.id.btn_SignUp);
        tvDangNhap = findViewById(R.id.tv_SignIn);

        final Intent intentDangNhap = new Intent(this, LoginActivity.class);

        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentDangNhap);
            }
        });

    }
}