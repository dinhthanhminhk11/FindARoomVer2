package com.example.findaroomver2.response.post;

import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PostHome {
    @SerializedName("data")
    private List<Post> data;
    @SerializedName("message")
    private Message message;

    public List<Post> getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
