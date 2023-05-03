package com.example.findaroomver2.response.bookmark;

import com.example.findaroomver2.request.bookmark.Bookmark;
import com.example.findaroomver2.request.favourite.Favourite;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

public class BookmarkResponse {
    @SerializedName("data")
    private Bookmark bookmark;
    @SerializedName("message")
    private Message message;

    public Bookmark getBookmark() {
        return bookmark;
    }

    public Message getMessage() {
        return message;
    }
}
