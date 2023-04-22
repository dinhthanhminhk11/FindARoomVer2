package com.example.findaroomver2.response;

import com.google.gson.annotations.SerializedName;

public class TextResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;

    public TextResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
