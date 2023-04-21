package com.example.findaroomver2.request.login;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("_id")
    private String id;
    @SerializedName("fullname")
    private String fullName;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("accessToken")
    private String accessToken;

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
