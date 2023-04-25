package com.example.findaroomver2.response.supplement;

import com.google.gson.annotations.SerializedName;

public class Supplement {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("iconImage")
    private String iconImage;

    private boolean checker;

    public boolean isChecker() {
        return checker;
    }

    public void setChecker(boolean checker) {
        this.checker = checker;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconImage() {
        return iconImage;
    }
}
