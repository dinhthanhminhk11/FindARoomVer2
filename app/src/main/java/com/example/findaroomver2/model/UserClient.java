package com.example.findaroomver2.model;

public class UserClient {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String tokenDevice;
    private int role;
    private static UserClient instance = null;
    protected UserClient() {
    }

    public static UserClient getInstance() {
        if (instance == null) {
            instance = new UserClient();
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
