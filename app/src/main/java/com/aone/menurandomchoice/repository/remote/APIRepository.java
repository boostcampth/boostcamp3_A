package com.aone.menurandomchoice.repository.remote;

import android.content.Context;
import android.content.SharedPreferences;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.model.EmptyObject;
import com.aone.menurandomchoice.repository.model.OwnerInfo;
import com.aone.menurandomchoice.repository.remote.response.JMTCallback;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.repository.remote.response.KakaoCallback;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.model.KakaoAddressResult;
import com.aone.menurandomchoice.utils.NetworkUtil;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class APIRepository implements APIHelper {

    private static APIRepository apiRepositoryInstance;
    private APICreator apiCreator;

    public static APIRepository getInstance() {
        if(apiRepositoryInstance == null) {
            apiRepositoryInstance = new APIRepository();
        }
        return apiRepositoryInstance;
    }

    private APIRepository() {
        setUp();
    }

    private void setUp() {
        apiCreator = new APICreator();
    }

    @Override
    public void executeLocationSearch(@NonNull String query,
                                      @NonNull NetworkResponseListener<KakaoAddressResult> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            String REST_API_KEY = GlobalApplication.getGlobalApplicationContext().getString(R.string.KAKAO_REST_API_KEY);
            apiCreator.getApiInstance()
                    .getAddress(REST_API_KEY, query)
                    .enqueue(new KakaoCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestMenuLocation(@NonNull Map<String, String> queryMap,
                                    @NonNull NetworkResponseListener<List<MenuLocation>> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getMenuLocation(queryMap)
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestStoreDetail(int storeIdx,
                                   @NonNull NetworkResponseListener<StoreDetail> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getStoreDetail(storeIdx)
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }

    }

    @Override
    public void checkStoreUpdated(int storeIdx,
                                  @NonNull String updateTime,
                                  @NonNull NetworkResponseListener<EmptyObject> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .checkStoreUpdated(storeIdx, updateTime)
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestSignedUpCheck(long userId,
                                     @NonNull NetworkResponseListener<LoginData> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getSignedUpCheckRequest(new OwnerInfo(String.valueOf(userId)))
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

//    @Override
//    public void requestSignedUpCheck(long userId, @NonNull OnSignedUpCheckListener onSignedUpCheckListener) {
//        try {
//            SharedPreferences pref = GlobalApplication.getGlobalApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
//            boolean isSignedUp = pref.getBoolean(String.valueOf(userId), false);
//            if(isSignedUp) {
//                onSignedUpCheckListener.onAlreadySignedUp();
//            } else {
//                onSignedUpCheckListener.onNotSignUp();
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    /**
     * do check sign up of userId from server
     * but we don't have a server yet.
     * so, I used virtual logic.
     */
    @Override
    public void requestSignUp(long userId, @NonNull String accessKey, @NonNull OnSignUpRequestListener onSignUpRequestListener) {
        try {
            SharedPreferences pref = GlobalApplication.getGlobalApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(String.valueOf(userId), true);
            editor.commit();

            onSignUpRequestListener.onSignUpSuccess();
        } catch (Exception e) {
            onSignUpRequestListener.onSignUpFail();
        }
    }

}
