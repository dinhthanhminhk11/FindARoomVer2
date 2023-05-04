package com.example.findaroomver2.model;


public class ContentChat {
    private Text message;
    private String send;
    private String sendTo;

    public ContentChat(Text message, String send, String sendTo) {
        this.message = message;
        this.send = send;
        this.sendTo = sendTo;
    }

    public Text getMessage() {
        return message;
    }

    public void setMessage(Text message) {
        this.message = message;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
