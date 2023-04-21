package com.example.findaroomver2.api;

import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.register.UserRegisterRequest;
import com.example.findaroomver2.response.UserResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiRequest {
    @POST("auth/login")
    Call<UserResponseLogin> login(@Body UserLoginRequest userLoginRequest);

    @GET("getUserByToken")
    Call<UserResponseLogin> getUserByToken(@Header("x-access-token") String token);

    @POST("auth/register")
    Call<UserResponseLogin> register(@Body UserRegisterRequest userRegisterRequest);
}
