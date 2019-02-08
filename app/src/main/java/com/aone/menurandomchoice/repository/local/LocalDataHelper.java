package com.aone.menurandomchoice.repository.local;

import com.aone.menurandomchoice.repository.model.StoreDetail;

public interface LocalDataHelper {

    void createStoreDetail(final StoreDetail storeDetail);

    StoreDetail getStoreDetailByStoreIdx(final int storeIdx);

    void updateStoreDetail(final StoreDetail storeDetail);

    void deleteStoreDetail(final int storeIdx);

}
