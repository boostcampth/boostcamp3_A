package com.aone.menurandomchoice.repository.remote.response;

import com.aone.menurandomchoice.repository.pojo.KakaoAddressResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponseBody {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("pageable_count")
    private int pageableCount;

    @SerializedName("is_end")
    private boolean isEnd;

    private List<KakaoAddressResult> documents;

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

    public List<KakaoAddressResult> getDocuments() {
        return documents;
    }

    public void setDocuments(List<KakaoAddressResult> documents) {
        this.documents = documents;
    }

}
