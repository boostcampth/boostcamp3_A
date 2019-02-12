package com.aone.menurandomchoice.repository.network.model;

import com.aone.menurandomchoice.repository.network.pojo.KakaoAddressResult;

import java.util.List;

public class AddressResponseBody {
    private int totalCount;
    private int pageableCount;
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
