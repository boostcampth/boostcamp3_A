package com.aone.menurandomchoice.repository.network.model;

import com.aone.menurandomchoice.repository.network.pojo.MenuLocation;

import java.util.List;

import okhttp3.Response;

public class MenuLocationResponseBody {
    private Integer status;
    private String message;
    private List<MenuLocation> data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MenuLocation> getData() {
        return data;
    }

    public void setData(List<MenuLocation> data) {
        this.data = data;
    }
}
