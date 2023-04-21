package com.example.findaroomver2.api;

import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.UserResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRequest {
    @POST("auth/login")
    Call<UserResponseLogin> login(@Body UserLoginRequest userLoginRequest);
}
