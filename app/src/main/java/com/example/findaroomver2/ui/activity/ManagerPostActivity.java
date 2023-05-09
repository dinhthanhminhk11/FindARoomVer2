package com.example.findaroomver2.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityManagerPostBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.post.PostHome;
import com.example.findaroomver2.ui.adapter.ManagerPostAdapter;
import com.example.findaroomver2.viewmodel.ManagerPostViewModel;

public class ManagerPostActivity extends AppCompatActivity {

    private ActivityManagerPostBinding binding;
    private ManagerPostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManagerPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initToolbar();
        initData();
    }

    private void initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolbar.setTitle("Quản lí bài đăng");
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void initData() {
        viewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        viewModel.getListMutableLiveDataPost().observe(this, new Observer<PostHome>() {
            @Override
            public void onChanged(PostHome postHome) {
                ManagerPostAdapter managerPostAdapter = new ManagerPostAdapter(postHome.getData());
                binding.listPost.setAdapter(managerPostAdapter);
            }
        });
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ManagerPostViewModel.class);
        binding.listPost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getListPostManager(UserClient.getInstance().getId());
    }
}