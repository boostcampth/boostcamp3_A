package com.aone.menurandomchoice.data.network.model;

import com.aone.menurandomchoice.data.network.pojo.AddressInfo;

import java.util.List;

public class AddressResponseBody {
    private int totalCount;
    private int pageableCount;
    private boolean isEnd;
    private List<AddressInfo> documents;

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

    public List<AddressInfo> getDocuments() {
        return documents;
    }

    public void setDocuments(List<AddressInfo> documents) {
        this.documents = documents;
    }
}
