package com.example.findaroomver2.request;

import com.google.gson.annotations.SerializedName;

public class ViewUpdatePost {
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("idPost")
    private String idPost;

    public ViewUpdatePost(String idUser, String idPost) {
        this.idUser = idUser;
        this.idPost = idPost;
    }
}
