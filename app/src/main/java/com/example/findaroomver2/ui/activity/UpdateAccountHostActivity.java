package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityUpdateAccountHostBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.updateUser.UserUpdateResponse;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.UpdateAccountViewModel;

public class UpdateAccountHostActivity extends AppCompatActivity {

    private ActivityUpdateAccountHostBinding binding;
    private UpdateAccountViewModel updateAccountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateAccountHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolBar.setTitle("");
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        updateAccountViewModel = new ViewModelProvider(this).get(UpdateAccountViewModel.class);

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccountViewModel.updateAccount(UserClient.getInstance().getId());
            }
        });
        updateAccountViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        updateAccountViewModel.getUserUpdateResponseMutableLiveData().observe(this, new Observer<UserUpdateResponse>() {
            @Override
            public void onChanged(UserUpdateResponse userUpdateResponse) {
                if (userUpdateResponse.getMessage().isStatus()) {
                    UserClient userClient = UserClient.getInstance();
                    userClient.setRole(userUpdateResponse.getData().getRole());
                    CustomToast.ct(UpdateAccountHostActivity.this, userUpdateResponse.getMessage().getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                    finish();
                } else {
                    CustomToast.ct(UpdateAccountHostActivity.this, userUpdateResponse.getMessage().getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                }
            }
        });
    }
}