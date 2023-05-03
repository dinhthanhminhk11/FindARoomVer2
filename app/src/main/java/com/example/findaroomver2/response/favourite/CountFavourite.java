package com.example.findaroomver2.response.favourite;

import com.example.findaroomver2.request.favourite.Favourite;
import com.example.findaroomver2.request.login.Message;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountFavourite {
    @SerializedName("data")
    private List<Favourite> data;
    @SerializedName("message")
    private Message message;

    public List<Favourite> getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
