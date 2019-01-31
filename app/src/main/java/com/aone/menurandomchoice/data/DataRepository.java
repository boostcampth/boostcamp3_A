package com.aone.menurandomchoice.data;

import android.content.Intent;

import com.aone.menurandomchoice.data.oauth.KakaoLoginAPI;
import com.aone.menurandomchoice.data.oauth.KakaoLoginError;
import com.aone.menurandomchoice.data.oauth.KakaoLoginRepository;
import com.aone.menurandomchoice.data.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.data.oauth.OnKakaoLogoutListener;

import androidx.annotation.Nullable;

public class DataRepository implements Repository {

    private static Repository repository;
    private KakaoLoginAPI kakaoLoginAPI;

    public static Repository getInstance() {
        if(repository == null) {
            repository = new DataRepository();
        }

        return repository;
    }

    private DataRepository() {
        setUpKakaoLoginAPI();
    }

    private void setUpKakaoLoginAPI() {
        KakaoLoginRepository kakaoLoginRepository = KakaoLoginRepository.getInstance();
        kakaoLoginRepository.setOnKakaoLoginListener(onKakaoLoginListener);
        kakaoLoginRepository.setOnKakaoLogoutListener(onKakaoLogoutListener);
        kakaoLoginAPI = kakaoLoginRepository;
    }

    @Override
    public void checkLoggedinAccount() {
        kakaoLoginAPI.checkLoggedinAccount();
    }

    @Override
    public void executeDeviceKakaoAccountLogin() {
        kakaoLoginAPI.executeDeviceKakaoAccountLogin();
    }

    @Override
    public void executeOtherKakaoAccountLogin() {
        kakaoLoginAPI.executeOtherKakaoAccountLogin();
    }

    @Override
    public void executeKakaoAccountLogout() {
        kakaoLoginAPI.executeKakaoAccountLogout();
    }

    @Override
    public boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data) {
        return kakaoLoginAPI.isNeedKakaoSDKLoginScreen(requestCode, resultCode, data);
    }

    private OnKakaoLoginListener onKakaoLoginListener = new OnKakaoLoginListener() {

        @Override
        public void onLoginSuccess(long userId) {
            // success kakao login and do sign up or sign in
        }

        @Override
        public void onFail(KakaoLoginError kakaoLoginError) {
            // fail kakao login and do error handling
        }

    };

    private OnKakaoLogoutListener onKakaoLogoutListener = new OnKakaoLogoutListener() {

        @Override
        public void onLogoutSuccess() {
            // success kakao logout and logout handling
        }

    };
}
