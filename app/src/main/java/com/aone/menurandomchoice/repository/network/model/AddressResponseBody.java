package com.aone.menurandomchoice.repository.network.model;

import com.aone.menurandomchoice.repository.network.pojo.KakaoAddressResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponseBody {

    private List<KakaoAddressResult> documents;

    public List<KakaoAddressResult> getDocuments() {
        return documents;
    }

}
