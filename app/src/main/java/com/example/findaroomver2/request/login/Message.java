package com.example.findaroomver2.request.login;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }
}
