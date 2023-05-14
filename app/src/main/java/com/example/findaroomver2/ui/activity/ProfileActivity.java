package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityProfileBinding;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.post.PostHome;
import com.example.findaroomver2.ui.adapter.postfragment.PostAdapter;
import com.example.findaroomver2.viewmodel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private PostAdapter postAdapter;
    private String idUser = "";
    private RequestOptions optionsUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();
        initData();
    }

    private void initData() {
        idUser = getIntent().getStringExtra(AppConstant.ID_USER);
        profileViewModel.getUserById(idUser);
        profileViewModel.getListPostManager(idUser);

        profileViewModel.getDataUser().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                binding.email.setText(data.getEmail());
                binding.profileName.setText(data.getFullName());
                binding.phone.setText(data.getPhone());
                binding.address.setText(data.getAddress());
                if (data.getRole() == 2) {
                    binding.profileStatus.setText("Chủ nhà");
                } else {
                    binding.profileStatus.setText("Người đi thuê");
                    binding.rcvPost.setVisibility(View.GONE);
                    binding.titlePost.setVisibility(View.GONE);
                }
                Glide.with(ProfileActivity.this).load(data.getImage()).apply(optionsUser).into(binding.profilePic);

                binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + data.getPhone()));
                        startActivity(intent);
                    }
                });
            }
        });

        profileViewModel.getListMutableLiveDataPost().observe(this, new Observer<PostHome>() {
            @Override
            public void onChanged(PostHome postHome) {
                postAdapter = new PostAdapter(postHome.getData());
                postAdapter.setCallback(new PostAdapter.Callback() {
                    @Override
                    public void onClickItem(Post post) {
                        Intent intent = new Intent(ProfileActivity.this, DetailActivity.class);
                        intent.putExtra(AppConstant.ID_POST, post.get_id());
                        startActivity(intent);
                    }

                    @Override
                    public void onClickMore(Post post) {

                    }

                    @Override
                    public void onClickAddHeart(Post post) {

                    }

                    @Override
                    public void onClickRemoteHeart(Post post) {

                    }
                });
                binding.rcvPost.setAdapter(postAdapter);
            }
        });

        profileViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });
    }

    private void initView() {
        optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.rcvPost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initToolbar() {
        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}