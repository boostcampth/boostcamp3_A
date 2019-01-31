package com.aone.menurandomchoice.data.oauth;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface KakaoLoginAPI {

    void checkLoggedinAccount();

    void executeDeviceKakaoAccountLogin();

    void executeOtherKakaoAccountLogin();

    void executeKakaoAccountLogout();

    boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data);

}
