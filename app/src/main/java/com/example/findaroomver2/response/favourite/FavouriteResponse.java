package com.example.findaroomver2.response.favourite;

import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.request.favourite.Favourite;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouriteResponse {
    @SerializedName("data")
    private Favourite favourite;
    @SerializedName("message")
    private Message message;

    public Favourite getFavourite() {
        return favourite;
    }

    public Message getMessage() {
        return message;
    }
}
