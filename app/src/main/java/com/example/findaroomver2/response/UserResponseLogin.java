package com.example.findaroomver2.response;

import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

public class UserResponseLogin {
    @SerializedName("data")
    private Data data;
    @SerializedName("message")
    private Message message;

    public Data getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
