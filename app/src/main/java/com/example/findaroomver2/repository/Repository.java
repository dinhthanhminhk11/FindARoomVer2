package com.example.findaroomver2.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.findaroomver2.api.ApiRequest;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.request.changepass.Email;
import com.example.findaroomver2.request.changepass.Verify;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.register.UserRegisterRequest;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.retrofit.AndroidUtilities;
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
        AndroidUtilities.runOnUIThread(() -> {
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
                    Log.e(AppConstant.CALL_ERROR, t.getMessage());
                }
            });
        });
    }

    public void loginByToken(String token, Consumer<UserResponseLogin> consumer) {
        AndroidUtilities.runOnUIThread(() -> {
            apiRequest.getUserByToken(token).enqueue(new Callback<UserResponseLogin>() {
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
                    Log.e(AppConstant.CALL_ERROR, t.getMessage());
                }
            });
        });
    }

    public void register(UserRegisterRequest userRegisterRequest, Consumer<UserResponseLogin> consumer) {
        AndroidUtilities.runOnUIThread(() -> {
            apiRequest.register(userRegisterRequest).enqueue(new Callback<UserResponseLogin>() {
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
                    Log.e(AppConstant.CALL_ERROR, t.getMessage());
                }
            });
        });
    }

    public void sendMailForgotPass(Email email, Consumer<TextResponse> consumer) {
        AndroidUtilities.runOnUIThread(() -> {
            apiRequest.checkEmail(email).enqueue(new Callback<TextResponse>() {
                @Override
                public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
                    if (response.isSuccessful()) {
                        consumer.accept(response.body());
                    } else {
                        Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<TextResponse> call, Throwable t) {
                    Log.e(AppConstant.CALL_ERROR, t.getMessage());
                }
            });
        });
    }

    public void checkOTPPass(Verify verify, Consumer<TextResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.checkOtpPass(verify).enqueue(new Callback<TextResponse>() {
                    @Override
                    public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<TextResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }

    public void newPassWord(UserLoginRequest userLoginRequest, Consumer<TextResponse> consumer) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                apiRequest.newPassWord(userLoginRequest).enqueue(new Callback<TextResponse>() {
                    @Override
                    public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
                        if (response.isSuccessful()) {
                            consumer.accept(response.body());
                        } else {
                            Log.e(AppConstant.CALL_ERROR, AppConstant.CALL_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<TextResponse> call, Throwable t) {
                        Log.e(AppConstant.CALL_ERROR, t.getMessage());
                    }
                });
            }
        });
    }


}
