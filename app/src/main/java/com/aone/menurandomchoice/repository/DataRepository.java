package com.aone.menurandomchoice.repository;

import android.content.Intent;

import com.aone.menurandomchoice.repository.local.pref.PreferencesHelper;
import com.aone.menurandomchoice.repository.local.pref.PreferencesRepository;
import com.aone.menurandomchoice.repository.pojo.MenuLocation;
import com.aone.menurandomchoice.repository.remote.APIHelper;
import com.aone.menurandomchoice.repository.remote.APIRepository;
import com.aone.menurandomchoice.repository.remote.OnCachedDataSameCheckListener;
import com.aone.menurandomchoice.repository.remote.response.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.AddressResponseBody;
import com.aone.menurandomchoice.repository.local.SqliteDatabaseHelper;
import com.aone.menurandomchoice.repository.local.SqliteDatabaseRepository;
import com.aone.menurandomchoice.repository.pojo.StoreDetail;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginHelper;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginRepository;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLogoutListener;
import com.aone.menurandomchoice.repository.remote.response.ResponseBody;
import com.aone.menurandomchoice.repository.remote.OnSignUpRequestListener;
import com.aone.menurandomchoice.repository.remote.OnSignedUpCheckListener;
import com.aone.menurandomchoice.utils.NetworkUtil;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataRepository implements Repository {

    private static Repository repository;
    private KakaoLoginHelper kakaoLoginHelper;
    private APIHelper apiHelper;
    private SqliteDatabaseHelper sqliteDatabaseHelper;
    private PreferencesHelper preferencesHelper;

    @NonNull
    public static Repository getInstance() {
        if (repository == null) {
            repository = new DataRepository();
        }

        return repository;
    }

    private DataRepository() {
        kakaoLoginHelper = KakaoLoginRepository.getInstance();
        apiHelper = APIRepository.getInstance();
        sqliteDatabaseHelper = SqliteDatabaseRepository.getInstance();
        preferencesHelper = new PreferencesRepository();
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
        apiHelper.requestSignedUpCheck(userId, onSignedUpCheckListener);
    }

    @Override
    public void requestSignUp(long userId, @NonNull String accessKey, @NonNull OnSignUpRequestListener onSignUpRequestListener) {
        apiHelper.requestSignUp(userId, accessKey, onSignUpRequestListener);
    }

    @Override
    public void executeLocationSearch(@NonNull String query,
                                      @NonNull NetworkResponseListener<AddressResponseBody> networkResponseListener) {
        apiHelper.executeLocationSearch(query, networkResponseListener);
    }

    @Override
    public void requestMenuLocation(@NonNull Map<String, String> queryMap,
                                    @NonNull NetworkResponseListener<ResponseBody<List<MenuLocation>>> networkResponseListener) {
        apiHelper.requestMenuLocation(queryMap, networkResponseListener);
    }

    @Override
    public void requestStoreDetail(final int storeIdx,
                                   @NonNull final OnLoadStoreDetailListener onLoadStoreDetailListener) {
        apiHelper.requestStoreDetail(storeIdx, onLoadStoreDetailListener);
    }

    @Override
    public void checkStoreUpdated(int storeIdx,
                                  @NonNull String updateTime,
                                  @NonNull OnCachedDataSameCheckListener onCachedDataSameCheckListener) {
        apiHelper.checkStoreUpdated(storeIdx, updateTime, onCachedDataSameCheckListener);
    }

    public void saveRegisteredImageLocalPath(@NonNull String path) {
        preferencesHelper.saveRegisteredImageLocalPath(path);
    }

    @NonNull
    @Override
    public String getSavedRegisterImageLoadPath() {
        return preferencesHelper.getSavedRegisterImageLoadPath();
    }

    @Override
    public void clearRegisteredImageLocalPath() {
        preferencesHelper.clearRegisteredImageLocalPath();
    }

    @Override
    public void loadStoreDetail(final int storeIdx,
                                @NonNull final OnLoadStoreDetailListener onLoadStoreDetailListener) {
        final StoreDetail cachedStoreDetail = getStoreDetail();

        if(NetworkUtil.isNetworkConnecting()) {
            checkStoreUpdated(storeIdx, cachedStoreDetail.getUpdateTime(), new OnCachedDataSameCheckListener() {
                @Override
                public void onCachedDataIsSame() {
                    onLoadStoreDetailListener.onStoreDetailLoaded(cachedStoreDetail);
                }

                @Override
                public void onCachedDataIsNotSame() {
                    requestStoreDetail(storeIdx, onLoadStoreDetailListener);
                }

                @Override
                public void onFail(@NonNull String errorMessage) {
                    onLoadStoreDetailListener.onFailToLoadStoreDetail(errorMessage);
                }
            });
        } else {
            onLoadStoreDetailListener.onFailToLoadStoreDetail("Network Connect Error");
        }
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
