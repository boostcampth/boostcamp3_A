package com.aone.menurandomchoice.repository.network.pojo;

import com.google.gson.annotations.SerializedName;

public class KakaoAddressResult {
    @SerializedName("address_name")
    private String addressName;
    private double y;
    private double x;

    public String getAddressName() {
        return addressName;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

}
