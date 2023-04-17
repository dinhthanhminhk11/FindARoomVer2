package com.example.findaroomver2.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.findaroomver2.api.ApiRequest;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.retrofit.RetrofitRequest;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NewApi")
public class Repository {
    private ApiRequest apiRequest;

    public Repository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void login(UserLoginRequest userLoginRequest, Consumer<UserResponseLogin> consumer) {
        apiRequest.login(userLoginRequest).enqueue(new Callback<UserResponseLogin>() {
            @Override
            public void onResponse(Call<UserResponseLogin> call, Response<UserResponseLogin> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                }
            }

            @Override
            public void onFailure(Call<UserResponseLogin> call, Throwable t) {
                Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
            }
        });
    }
}
