package com.example.findaroomver2.model;

import com.example.findaroomver2.request.login.Data;

import java.util.List;

public class DataUser {
    private List<Data> data;

    public DataUser(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
