package com.example.findaroomver2.request.comment;

public class Comment {
    private String _id;
    private String idUser;
    private String idPost;
    private String timeLong;
    private String content;
    private String parentCommentId;
    private int __v;

    public Comment(String idUser, String idPost, String content) {
        this.idUser = idUser;
        this.idPost = idPost;
        this.content = content;
    }

    public Comment(String idUser, String idPost, String content, String parentCommentId) {
        this.idUser = idUser;
        this.idPost = idPost;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    public String get_id() {
        return _id;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdPost() {
        return idPost;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public String getContent() {
        return content;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public int get__v() {
        return __v;
    }
}
