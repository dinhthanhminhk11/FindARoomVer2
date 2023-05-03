package com.example.findaroomver2.request.bookmark;

import com.google.gson.annotations.SerializedName;

public class Bookmark {
    @SerializedName("_id")
    private String id;
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("idPost")
    private String idPost;
    @SerializedName("isCheck")
    private boolean check;

    public Bookmark(String idUser, String idPost) {
        this.idUser = idUser;
        this.idPost = idPost;
    }

    public String getId() {
        return id;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdPost() {
        return idPost;
    }

    public boolean isCheck() {
        return check;
    }
}
