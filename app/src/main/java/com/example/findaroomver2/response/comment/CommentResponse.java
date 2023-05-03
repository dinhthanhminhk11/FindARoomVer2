package com.example.findaroomver2.response.comment;

import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.request.favourite.Favourite;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentResponse {
    @SerializedName("data")
    private Comment data;
    @SerializedName("message")
    private Message message;

    public Comment getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
