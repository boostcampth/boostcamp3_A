package com.aone.menurandomchoice.repository.oauth;


import androidx.annotation.NonNull;

public interface OnKakaoLoginListener {

    void onKakaoLoginSuccess(long userId);

    void onFail(@NonNull KakaoLoginError kakaoLoginError);

}

