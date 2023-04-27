package com.example.findaroomver2.response.post;

import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

public class PostResponse {
    @SerializedName("data")
    private Post data;
    @SerializedName("message")
    private Message message;

    public Post getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
