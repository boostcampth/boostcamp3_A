package com.aone.menurandomchoice.repository;

import com.aone.menurandomchoice.repository.local.pref.PreferencesHelper;
import com.aone.menurandomchoice.repository.remote.APIHelper;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginHelper;
import com.aone.menurandomchoice.repository.local.SqliteDatabaseHelper;
import com.aone.menurandomchoice.repository.pojo.StoreDetail;

import androidx.annotation.NonNull;

public interface Repository extends KakaoLoginHelper, SqliteDatabaseHelper, APIHelper, PreferencesHelper {

    interface OnLoadStoreDetailListener {

        void onStoreDetailLoaded(StoreDetail storeDetail);

        void onFailToLoadStoreDetail(String errorMessage);

    }

    void cancelAll();

    void loadStoreDetail(int storeIdx, @NonNull OnLoadStoreDetailListener onLoadStoreDetailListener);

}
