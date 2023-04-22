package com.example.findaroomver2.request.login;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
