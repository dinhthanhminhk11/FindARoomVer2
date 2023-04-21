package com.example.findaroomver2.request.register;

import com.google.gson.annotations.SerializedName;

public class UserRegisterRequest {
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("role")
    private int role;
    @SerializedName("tokenDevice")
    private String tokenDevice;

    public UserRegisterRequest(String phone, String email, String password, String fullname, int role, String tokenDevice) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.tokenDevice = tokenDevice;
    }
}
