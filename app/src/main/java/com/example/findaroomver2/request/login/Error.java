package com.example.findaroomver2.request.login;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }
}
