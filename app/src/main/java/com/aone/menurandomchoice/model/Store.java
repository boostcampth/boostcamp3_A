package com.aone.menurandomchoice.model;

import java.util.Date;
import java.util.List;

public class Store {
    private String name;
    private String time;
    private String address;
    private String description;
    private double latitude;
    private double longitude;
    private List<Menu> menuList;

    public Store(String name, String time, String address, String description, double latitude, double longitude, List<Menu> menuList) {
        this.name = name;
        this.time = time;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.menuList = menuList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
