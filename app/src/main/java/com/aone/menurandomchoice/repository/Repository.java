package com.aone.menurandomchoice.repository;

import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.repository.remote.APIHelper;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginHelper;
import com.aone.menurandomchoice.repository.local.db.SQLiteDatabaseHelper;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;

import androidx.annotation.NonNull;

public interface Repository extends KakaoLoginHelper, SQLiteDatabaseHelper, APIHelper {

    void cancelAll();

    void loadStoreDetail(UserAccessInfo userAccessInfo, @NonNull NetworkResponseListener<StoreDetail> networkResponseListener);

}
