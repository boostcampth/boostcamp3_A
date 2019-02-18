package com.aone.menurandomchoice.repository.model;

import com.google.gson.annotations.SerializedName;

public class KakaoAddress {

    // make annotation for reusing this class as a mock model
/*
    @SerializedName("road_address")
    private Object roadAddress;

    @SerializedName("address_type")
    private String addressType;
*/

    @SerializedName("address_name")
    private String addressName;


    @SerializedName("category_group_code")
    private String categoryGroupCode;

    private double y;

    private double x;

    public String getCategoryGroupCode() { return categoryGroupCode; }

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
