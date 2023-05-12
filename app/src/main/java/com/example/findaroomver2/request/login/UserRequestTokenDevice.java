package com.example.findaroomver2.request.login;

import com.google.gson.annotations.SerializedName;

public class UserRequestTokenDevice {
    @SerializedName("id")
    private String id;
    @SerializedName("tokenDevice")
    private String tokenDevice;

    public UserRequestTokenDevice(String id, String tokenDevice) {
        this.id = id;
        this.tokenDevice = tokenDevice;
    }
}
