package com.example.findaroomver2.api;

import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.request.changepass.Email;
import com.example.findaroomver2.request.changepass.Verify;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.register.UserRegisterRequest;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.response.supplement.DataSupplement;
import com.example.findaroomver2.response.supplement.Supplement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiRequest {
    @POST("auth/login")
    Call<UserResponseLogin> login(@Body UserLoginRequest userLoginRequest);

    @GET("auth/getUserByToken")
    Call<UserResponseLogin> getUserByToken(@Header("x-access-token") String token);

    @POST("auth/register")
    Call<UserResponseLogin> register(@Body UserRegisterRequest userRegisterRequest);

    @POST("auth/checkEmailForgot")
    Call<TextResponse> checkEmail(@Body Email email);

    @POST("auth/validateUserPass")
    Call<TextResponse> checkOtpPass(@Body Verify verify);

    @POST("auth/newPass")
    Call<TextResponse> newPassWord(@Body UserLoginRequest userLogin);

    @GET("supplements/getAllSupplements")
    Call<DataSupplement> getListSupplement();

    @POST("post")
    Call<PostResponse> createPost(@Body Post post);
}
