package com.example.findaroomver2.response.bookmark;

import com.example.findaroomver2.request.bookmark.Bookmark;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListBookmarkResponse {
    @SerializedName("data")
    private List<Bookmark> data;
    @SerializedName("message")
    private Message message;

    public List<Bookmark> getBookmark() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
