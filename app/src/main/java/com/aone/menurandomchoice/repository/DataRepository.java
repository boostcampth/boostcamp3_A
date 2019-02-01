package com.aone.menurandomchoice.repository;

import android.content.Intent;

import com.aone.menurandomchoice.repository.oauth.KakaoLoginHelper;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginRepository;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLogoutListener;
import com.aone.menurandomchoice.repository.server.OnSignUpRequestListener;
import com.aone.menurandomchoice.repository.server.OnSignedUpCheckListener;
import com.aone.menurandomchoice.repository.server.ServerDataHelper;
import com.aone.menurandomchoice.repository.server.ServerDataRepository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataRepository implements Repository {

    private static Repository repository;
    private KakaoLoginHelper kakaoLoginHelper;
    private ServerDataHelper serverDataHelper;

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
    }

    @Override
    public void checkLoggedinAccount(OnKakaoLoginListener onKakaoLoginListener) {
        kakaoLoginHelper.checkLoggedinAccount(onKakaoLoginListener);
}

    @Override
    public void executeDeviceKakaoAccountLogin(OnKakaoLoginListener onKakaoLoginListener) {
        kakaoLoginHelper.executeDeviceKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public void executeOtherKakaoAccountLogin(OnKakaoLoginListener onKakaoLoginListener) {
        kakaoLoginHelper.executeOtherKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public void executeKakaoAccountLogout(OnKakaoLogoutListener onKakaoLogoutListener) {
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
    public void requestSignUp(long userId, String accessKey, OnSignUpRequestListener onSignUpRequestListener) {
        serverDataHelper.requestSignUp(userId, accessKey, onSignUpRequestListener);
    }

    @Override
    public void cancelAll() {
        // do cancel request
    }

}
