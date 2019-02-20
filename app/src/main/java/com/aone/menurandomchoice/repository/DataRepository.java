package com.aone.menurandomchoice.repository;

import android.content.Intent;
import android.util.Log;

import com.aone.menurandomchoice.repository.local.db.SQLiteDatabaseHelper;
import com.aone.menurandomchoice.repository.local.db.SQLiteDatabaseRepository;
import com.aone.menurandomchoice.repository.model.EmptyObject;
import com.aone.menurandomchoice.repository.model.UpdateTime;
import com.aone.menurandomchoice.repository.model.KakaoAddressResult;
import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginHelper;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginRepository;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLogoutListener;
import com.aone.menurandomchoice.repository.remote.APIHelper;
import com.aone.menurandomchoice.repository.remote.APIRepository;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataRepository implements Repository {

    private static Repository repository;
    private KakaoLoginHelper kakaoLoginHelper;
    private APIHelper apiHelper;
    private SQLiteDatabaseHelper SQLiteDatabaseHelper;

    private boolean isUpdated = false;

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
        SQLiteDatabaseHelper = SQLiteDatabaseRepository.getInstance();
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
    public void requestSignedUpCheck(long userId, @NonNull NetworkResponseListener<LoginData> networkResponseListener) {
        apiHelper.requestSignedUpCheck(userId, networkResponseListener);
    }

    @Override
    public void requestSignUp(long userId,
                              @NonNull String accessKey,
                              @NonNull NetworkResponseListener<LoginData> networkResponseListener) {
        apiHelper.requestSignUp(userId, accessKey, networkResponseListener);
    }

    @Override
    public void executeLocationSearch(@NonNull String query,
                                      @NonNull NetworkResponseListener<KakaoAddressResult> networkResponseListener) {
        apiHelper.executeLocationSearch(query, networkResponseListener);
    }

    @Override
    public void requestMenuLocation(@NonNull Map<String, String> queryMap,
                                    @NonNull NetworkResponseListener<List<MenuLocation>> networkResponseListener) {
        apiHelper.requestMenuLocation(queryMap, networkResponseListener);
    }

    @Override
    public void requestStoreDetail(int storeIdx,
                                   @NonNull NetworkResponseListener<StoreDetail> networkResponseListener) {
        apiHelper.requestStoreDetail(storeIdx, networkResponseListener);
    }

    @Override
    public void checkStoreUpdated(int storeIdx,
                                  @NonNull NetworkResponseListener<UpdateTime> networkResponseListener) {
        apiHelper.checkStoreUpdated(storeIdx, networkResponseListener);
    }

    @Override
    public void requestMenuList(@NonNull MenuSearchRequest menuSearchRequest,
                                                 @NonNull NetworkResponseListener<List<MenuDetail>> networkResponseListener) {
        apiHelper.requestMenuList(menuSearchRequest, networkResponseListener);
    }

    @Override
    public void loadStoreDetail(final int storeIdx,
                                final @NonNull NetworkResponseListener<StoreDetail> networkResponseListener) {

        final StoreDetail SQLiteStoreDetail = getStoreDetail();
        final String SQLiteUpdateTime = SQLiteStoreDetail.getUpdateTime();

        if(isUpdated) {
            networkResponseListener.onReceived(SQLiteStoreDetail);
        } else {
            checkStoreUpdated(storeIdx, new NetworkResponseListener<UpdateTime>() {

                @Override
                public void onReceived(@NonNull UpdateTime response) {
                    String ServerUpdateTime = response.getUpdateTime();

                    requestStoreDetail(storeIdx, networkResponseListener);
                    //Todo 회원가입 후 앱을 삭제하고 다시 실행할때 sqlite가 null이여서 np오류가 남 -> 수정예정
                    /*
                    if (ServerUpdateTime.equals(SQLiteUpdateTime)) {
                        networkResponseListener.onReceived(SQLiteStoreDetail);
                    } else {
                        requestStoreDetail(storeIdx, new NetworkResponseListener<StoreDetail>() {
                            @Override
                            public void onReceived(@NonNull StoreDetail response) {
                                updateStoreDetail(response);
                                networkResponseListener.onReceived(response);
                            }

                            @Override
                            public void onError(JMTErrorCode errorCode) {
                                isUpdated = false;
                                networkResponseListener.onError(errorCode);
                            }
                        });
                    }

                    isUpdated = true;*/
                }

                @Override
                public void onError(JMTErrorCode errorCode) {
                    networkResponseListener.onError(errorCode);
                }
            });
        }

    }

    @Override
    public void addDefaultStoreDetail() {
        SQLiteDatabaseHelper.addDefaultStoreDetail();
    }

    @Override
    public StoreDetail getStoreDetail() {
        return SQLiteDatabaseHelper.getStoreDetail();
    }

    @Override
    public void updateStoreDetail(@NonNull final StoreDetail storeDetail) {
        SQLiteDatabaseHelper.updateStoreDetail(storeDetail);
    }

    @Override
    public void requestSaveStoreDetail(@NonNull StoreDetail storeDetail, @NonNull NetworkResponseListener<EmptyObject> networkResponseListener) {
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        storeDetail.setUpdateTime(nowTime);
        isUpdated = false;

        apiHelper.requestSaveStoreDetail(storeDetail, networkResponseListener);
    }

    @Override
    public void cancelAll() {
        // do cancel request
    }

}
