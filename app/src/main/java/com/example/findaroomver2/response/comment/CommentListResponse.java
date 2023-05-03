package com.example.findaroomver2.response.comment;

import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentListResponse {
    @SerializedName("data")
    private List<Comment> data;
    @SerializedName("message")
    private Message message;

    public List<Comment> getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
