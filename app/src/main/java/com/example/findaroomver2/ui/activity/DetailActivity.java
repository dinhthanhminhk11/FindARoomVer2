package com.example.findaroomver2.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityDetailBinding;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.viewmodel.DetailPostViewModel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailBinding binding;
    private DetailPostViewModel detailPostViewModel;
    private String idPost = "";
    private NumberFormat fm = new DecimalFormat("#,###");
    private RequestOptions options;
    private String contactPhone;
    private List<String> listImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();
        initData();

    }

    private void initData() {
        idPost = getIntent().getStringExtra(AppConstant.ID_POST);
        detailPostViewModel.getPostById(idPost);
        detailPostViewModel.getPostResponseMutableLiveData().observe(this, new Observer<PostResponse>() {
            @Override
            public void onChanged(PostResponse postResponse) {
                binding.title.setText(postResponse.getData().getTitle());
                binding.address.setText(postResponse.getData().getAddress() + ", " + postResponse.getData().getStreet() + ", " + postResponse.getData().getWards() + ", " + postResponse.getData().getDistrict() + ", " + postResponse.getData().getCty());
                binding.price.setText(fm.format(postResponse.getData().getPrice()) + " VND");
                binding.content.setText(postResponse.getData().getDescribe());
                binding.acreage.setText(postResponse.getData().getAcreage() + " m²");
                binding.depositMoney.setText(fm.format(postResponse.getData().getDeposit()) + " VND");
                binding.bedroom.setText(postResponse.getData().getBedroom() + " phòng");
                binding.bathroom.setText(postResponse.getData().getBathroom() + " phòng");
                binding.coutPerson.setText(postResponse.getData().getCountPerson() + " người");
                binding.startDay.setText(postResponse.getData().getStartDate());
                binding.electricityPrice.setText(fm.format(postResponse.getData().getElectricityPrice()) + " VND/Số");
                binding.waterPrice.setText(fm.format(postResponse.getData().getWaterPrice()) + " VND/Số");
                binding.wifi.setText(fm.format(postResponse.getData().getWifi()) + " VND/Tháng");

                if (postResponse.getData().isStatusRoom()) {
                    binding.statusRoom.setText("Còn phòng");
                } else {
                    binding.statusRoom.setText("Hết phòng");
                }
                if (postResponse.getData().getImages().size() == 1) {

                    Glide.with(binding.contentImage1.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(binding.contentImage1);
                    binding.contentImage1.setVisibility(View.VISIBLE);
                    binding.contentImage2.setVisibility(View.GONE);
                    binding.contentImage3.setVisibility(View.GONE);
                    binding.contentImage4.setVisibility(View.GONE);
                } else if (postResponse.getData().getImages().size() == 2) {
                    Glide.with(binding.contentImage2Image1.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(binding.contentImage2Image1);
                    Glide.with(binding.contentImage2Image2.getContext()).load(postResponse.getData().getImages().get(1)).apply(options).into(binding.contentImage2Image2);

                    binding.contentImage1.setVisibility(View.GONE);
                    binding.contentImage2.setVisibility(View.VISIBLE);
                    binding.contentImage3.setVisibility(View.GONE);
                    binding.contentImage4.setVisibility(View.GONE);
                } else if (postResponse.getData().getImages().size() == 3) {
                    Glide.with(binding.contentImage3Image1.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(binding.contentImage3Image1);
                    Glide.with(binding.contentImage3Image2.getContext()).load(postResponse.getData().getImages().get(1)).apply(options).into(binding.contentImage3Image2);
                    Glide.with(binding.contentImage3Image3.getContext()).load(postResponse.getData().getImages().get(2)).apply(options).into(binding.contentImage3Image3);

                    binding.contentImage1.setVisibility(View.GONE);
                    binding.contentImage2.setVisibility(View.GONE);
                    binding.contentImage3.setVisibility(View.VISIBLE);
                    binding.contentImage4.setVisibility(View.GONE);
                } else {

                    Glide.with(binding.contentImage4Image1.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(binding.contentImage4Image1);
                    Glide.with(binding.contentImage4Image2.getContext()).load(postResponse.getData().getImages().get(1)).apply(options).into(binding.contentImage4Image2);
                    Glide.with(binding.contentImage4Image3.getContext()).load(postResponse.getData().getImages().get(2)).apply(options).into(binding.contentImage4Image3);
                    Glide.with(binding.contentImage4Image4.getContext()).load(postResponse.getData().getImages().get(3)).apply(options).into(binding.contentImage4Image4);

                    if (postResponse.getData().getImages().size() > 4) {
                        binding.countImage.setVisibility(View.VISIBLE);
                        binding.countImage.setText("+" + (postResponse.getData().getImages().size() - 4));
                        binding.checkImage4.setBackgroundResource(R.drawable.gradient_bg);
                    } else {
                        binding.countImage.setVisibility(View.GONE);
                        binding.checkImage4.setBackground(null);
                    }

                    binding.contentImage1.setVisibility(View.GONE);
                    binding.contentImage2.setVisibility(View.GONE);
                    binding.contentImage3.setVisibility(View.GONE);
                    binding.contentImage4.setVisibility(View.VISIBLE);
                }
                binding.btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + postResponse.getData().getPhone()));
                        startActivity(intent);
                    }
                });

                listImage = postResponse.getData().getImages();

            }
        });


    }

    private void initView() {
        listImage = new ArrayList<>();
        detailPostViewModel = new ViewModelProvider(this).get(DetailPostViewModel.class);
        options = new RequestOptions().centerCrop().placeholder(R.drawable.noimage).error(R.drawable.noimage);
        binding.contentImage1.setOnClickListener(this);
        binding.contentImage2.setOnClickListener(this);
        binding.contentImage3.setOnClickListener(this);
        binding.contentImage4.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int idView = view.getId();
        if (idView == R.id.contentImage1 || idView == R.id.contentImage2 || idView == R.id.contentImage3 || idView == R.id.contentImage4) {
            Intent intent = new Intent(DetailActivity.this, DetailImagePostActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstant.IMAGE_GALLERY, (Serializable) listImage);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}