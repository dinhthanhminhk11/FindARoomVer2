package com.example.findaroomver2.request.money;

public class CashFlowRequest {
    private String idUser;
    private boolean status;
    private String title;
    private String content;
    private int price;

    public CashFlowRequest(String idUser, boolean status, String title, String content, int price) {
        this.idUser = idUser;
        this.status = status;
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
