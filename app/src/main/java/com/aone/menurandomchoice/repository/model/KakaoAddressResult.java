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

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageableCount() {
        return pageableCount;
    }

    public void setPageableCount(int pageableCount) {
        this.pageableCount = pageableCount;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public List<KakaoAddress> getDocuments() {
        return documents;
    }

    public void setDocuments(List<KakaoAddress> documents) {
        this.documents = documents;
    }

}
