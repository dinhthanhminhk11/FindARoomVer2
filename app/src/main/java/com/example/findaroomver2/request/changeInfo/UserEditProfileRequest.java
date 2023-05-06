package com.example.findaroomver2.request.changeInfo;

import com.google.gson.annotations.SerializedName;

public class UserEditProfileRequest {
    @SerializedName("id")
    private String id;
    @SerializedName("fullname")
    private String fullName;
    @SerializedName("image")
    private String image;
    @SerializedName("personId")
    private String personId;
    @SerializedName("address")
    private String address;

    public UserEditProfileRequest(String id, String fullName, String image, String personId, String address) {
        this.id = id;
        this.fullName = fullName;
        this.image = image;
        this.personId = personId;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImage() {
        return image;
    }

    public String getPersonId() {
        return personId;
    }

    public String getAddress() {
        return address;
    }
}
