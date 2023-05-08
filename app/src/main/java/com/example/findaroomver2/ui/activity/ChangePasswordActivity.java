package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.findaroomver2.MainActivity;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityChangePasswordBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.changepass.ChangePasswordRequest;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.ChangePassViewModel;

public class ChangePasswordActivity extends AppCompatActivity {
    private ActivityChangePasswordBinding binding;
    private ChangePassViewModel changePassViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initData();

    }

    private void initView() {

        changePassViewModel = new ViewModelProvider(this).get(ChangePassViewModel.class);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnChangPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.passOld.getText().toString().isEmpty()) {
                    CustomToast.ct(ChangePasswordActivity.this, "Nhập mật khẩu cũ", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.passNew.getText().toString().isEmpty()) {
                    CustomToast.ct(ChangePasswordActivity.this, "Nhập mật khẩu mới", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.passNewAgain.getText().toString().isEmpty()) {
                    CustomToast.ct(ChangePasswordActivity.this, "Nhập lại mật khẩu mới", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.passNew.length() <= 6) {
                    CustomToast.ct(ChangePasswordActivity.this, "Mật khẩu phải trên 6 kí tự", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (!binding.passNew.getText().toString().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
                    CustomToast.ct(ChangePasswordActivity.this, "Mật khẩu ít nhất chứa 1 chữ số và 1 chữ cái", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (!binding.passNewAgain.getText().toString().equals(binding.passNew.getText().toString())) {
                    CustomToast.ct(ChangePasswordActivity.this, "Mật khẩu không trùng khớp", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else {
                    changePassViewModel.changePassword(new ChangePasswordRequest(UserClient.getInstance().getId(), binding.passOld.getText().toString(), binding.passNewAgain.getText().toString()));
                }
            }
        });
    }

    private void initData() {
        changePassViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        changePassViewModel.getTextResponseMutableLiveData().observe(this, new Observer<TextResponse>() {
            @Override
            public void onChanged(TextResponse textResponse) {
                if (textResponse.isStatus()) {
                    CustomToast.ct(ChangePasswordActivity.this, textResponse.getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                    finish();
                } else {
                    CustomToast.ct(ChangePasswordActivity.this, textResponse.getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                }
            }
        });
    }
}