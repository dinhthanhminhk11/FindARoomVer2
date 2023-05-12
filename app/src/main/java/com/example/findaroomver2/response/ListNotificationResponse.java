package com.example.findaroomver2.response;

import com.example.findaroomver2.model.NotificationModel;
import com.example.findaroomver2.request.login.Message;

import java.util.List;

public class ListNotificationResponse {
    private List<NotificationModel> data;
    private Message message;

    public List<NotificationModel> getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
