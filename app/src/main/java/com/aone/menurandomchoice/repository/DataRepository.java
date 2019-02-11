package com.aone.menurandomchoice.repository;

import android.content.Intent;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.repository.local.SqliteDatabaseHelper;
import com.aone.menurandomchoice.repository.local.SqliteDatabaseRepository;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginHelper;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginRepository;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLogoutListener;
import com.aone.menurandomchoice.repository.server.OnStoreUpdatedCheckListener;
import com.aone.menurandomchoice.repository.server.OnSignUpRequestListener;
import com.aone.menurandomchoice.repository.server.OnSignedUpCheckListener;
import com.aone.menurandomchoice.repository.server.OnStoreDetailRequestListener;
import com.aone.menurandomchoice.repository.server.ServerDataHelper;
import com.aone.menurandomchoice.repository.server.ServerDataRepository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataRepository implements Repository {

    private static Repository repository;
    private KakaoLoginHelper kakaoLoginHelper;
    private ServerDataHelper serverDataHelper;
    private SqliteDatabaseHelper sqliteDatabaseHelper;

    @NonNull
    public static Repository getInstance() {
        if(repository == null) {
            repository = new DataRepository();
        }

        return repository;
    }

    private DataRepository() {
        kakaoLoginHelper = KakaoLoginRepository.getInstance();
        serverDataHelper = ServerDataRepository.getInstance();
        sqliteDatabaseHelper = SqliteDatabaseRepository.getInstance();
    }

    @Override
    public void checkLoggedinAccount(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        kakaoLoginHelper.checkLoggedinAccount(onKakaoLoginListener);
}

    @Override
    public void executeDeviceKakaoAccountLogin(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        kakaoLoginHelper.executeDeviceKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public void executeOtherKakaoAccountLogin(@NonNull OnKakaoLoginListener onKakaoLoginListener) {
        kakaoLoginHelper.executeOtherKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public void executeKakaoAccountLogout(@NonNull OnKakaoLogoutListener onKakaoLogoutListener) {
        kakaoLoginHelper.executeKakaoAccountLogout(onKakaoLogoutListener);
    }

    @Override
    public boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data) {
        return kakaoLoginHelper.isNeedKakaoSDKLoginScreen(requestCode, resultCode, data);
    }

    @Override
    public void requestSignedUpCheck(long userId, @NonNull OnSignedUpCheckListener onSignedUpCheckListener) {
        serverDataHelper.requestSignedUpCheck(userId, onSignedUpCheckListener);
    }

    @Override
    public void requestSignUp(long userId,@NonNull  String accessKey, @NonNull OnSignUpRequestListener onSignUpRequestListener) {
        serverDataHelper.requestSignUp(userId, accessKey, onSignUpRequestListener);
    }

    @Override
    public void loadStoreDetail(final int storeIdx, @NonNull final OnLoadStoreDetailListener onLoadStoreDetailListener) {

        final StoreDetail cachedStoreDetail = getStoreDetail();

        checkStoreUpdated(cachedStoreDetail.getTime(), new OnStoreUpdatedCheckListener() {
            @Override
            public void onAlreadyStoreUpdated() {
                onLoadStoreDetailListener.onStoreDetailLoaded(cachedStoreDetail);
            }

            @Override
            public void onNotUpdated(@NonNull OnStoreDetailRequestListener onStoreDetailRequestListener) {
                requestStoreDetail(storeIdx, onStoreDetailRequestListener);
            }
        });

    }

    @Override
    public void checkStoreUpdated(@NonNull String updateTime, @NonNull OnStoreUpdatedCheckListener onStoreUpdatedCheckListener) {
        serverDataHelper.checkStoreUpdated(updateTime, onStoreUpdatedCheckListener);
    }

    @Override
    public void requestStoreDetail(int storeIdx, @NonNull OnStoreDetailRequestListener onStoreDetailRequestListener) {
        serverDataHelper.requestStoreDetail(storeIdx, onStoreDetailRequestListener);
    }


    @Override
    public void addStoreDetail() {
        sqliteDatabaseHelper.addStoreDetail();
    }

    @Override
    public StoreDetail getStoreDetail() {
        return sqliteDatabaseHelper.getStoreDetail();
    }

    @Override
    public void updateStoreDetail(@NonNull final StoreDetail storeDetail) {
        sqliteDatabaseHelper.updateStoreDetail(storeDetail);
    }


    @Override
    public void cancelAll() {
        // do cancel request
    }

}
