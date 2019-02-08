package com.aone.menurandomchoice.repository.local;

import com.aone.menurandomchoice.repository.model.StoreDetail;

public class LocalDataRepository implements LocalDataHelper {

    private static LocalDataRepository localDataRepository;

    public static LocalDataRepository getInstance() {
        if(localDataRepository == null) {
            localDataRepository = new LocalDataRepository();
        }

        return localDataRepository;
    }


    @Override
    public void createStoreDetail(final StoreDetail storeDetail) {
    }

    @Override
    public StoreDetail getStoreDetailByStoreIdx(final int storeIdx) {
        return null;
    }

    @Override
    public void updateStoreDetail(final StoreDetail storeDetail) {
    }

    @Override
    public void deleteStoreDetail(final int storeIdx) {
    }
}
