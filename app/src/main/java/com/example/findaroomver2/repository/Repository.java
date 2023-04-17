package com.example.findaroomver2.repository;

import android.annotation.SuppressLint;

import com.example.findaroomver2.api.ApiRequest;
import com.example.findaroomver2.retrofit.RetrofitRequest;

@SuppressLint("NewApi")
public class Repository {
    private ApiRequest apiRequest;

    public Repository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }
}
