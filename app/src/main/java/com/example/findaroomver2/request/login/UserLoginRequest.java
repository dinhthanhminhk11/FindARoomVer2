package com.example.findaroomver2.request.login;

import com.google.gson.annotations.SerializedName;

public class UserLoginRequest {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
