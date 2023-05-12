package com.example.findaroomver2.model;

public class NotificationModel {
    private String _id;
    private String idPost;
    private String idUser;
    private String title;
    private String content;
    private int type;
    private String imagePost;
    private long timeLong;
    private boolean isSeem;
    private int __v;

    public String get_id() {
        return _id;
    }

    public String getIdPost() {
        return idPost;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getImagePost() {
        return imagePost;
    }

    public long getTimeLong() {
        return timeLong;
    }

    public boolean isSeem() {
        return isSeem;
    }

    public int get__v() {
        return __v;
    }
}
