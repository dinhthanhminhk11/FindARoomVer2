package com.example.findaroomver2.response.supplement;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSupplement {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Supplement> data;

    public String getMessage() {
        return message;
    }

    public List<Supplement> getData() {
        return data;
    }
}
