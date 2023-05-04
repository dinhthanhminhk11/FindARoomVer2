package com.example.findaroomver2.model;

import com.airbnb.lottie.animation.content.Content;

import java.util.List;

public class DataChat {
    private List<ContentChat> data;

    public DataChat(List<ContentChat> data) {
        this.data = data;
    }

    public List<ContentChat> getData() {
        return data;
    }

    public void setData(List<ContentChat> data) {
        this.data = data;
    }
}
