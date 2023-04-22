package com.example.findaroomver2.api;

import com.example.findaroomver2.request.changepass.Email;
import com.example.findaroomver2.request.changepass.Verify;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.register.UserRegisterRequest;
import com.example.findaroomver2.response.TextResponse;
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

    @POST("auth/checkEmailForgot")
    Call<TextResponse> checkEmail(@Body Email email);

    @POST("auth/validateUserPass")
    Call<TextResponse> checkOtpPass(@Body Verify verify);

    @POST("auth/newPass")
    Call<TextResponse> newPassWord(@Body UserLoginRequest userLogin);
}
