package com.example.findaroomver2.request.login;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("_id")
    private String id;
    @SerializedName("fullname")
    private String fullName;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("tokenDevice")
    private String tokenDevice;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("image")
    private String image;
    @SerializedName("address")
    private String address;
    @SerializedName("personId")
    private String personId;
    @SerializedName("role")
    private int role;
    @SerializedName("textReport")
    private String textReport;
    @SerializedName("verified")
    private boolean verified;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getPersonId() {
        return personId;
    }

    public String getTextReport() {
        return textReport;
    }

    public boolean isVerified() {
        return verified;
    }
}
