package com.aone.menurandomchoice.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KakaoAddressResult {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("pageable_count")
    private int pageableCount;

    @SerializedName("is_end")
    private boolean isEnd;

    private List<KakaoAddress> documents;

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageableCount() {
        return pageableCount;
    }

    public List<KakaoAddress> getDocuments() {
        return documents;
    }

}
