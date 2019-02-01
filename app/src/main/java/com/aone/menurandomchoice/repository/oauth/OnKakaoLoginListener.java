package com.aone.menurandomchoice.repository.oauth;


public interface OnKakaoLoginListener {

    void onKakaoLoginSuccess(long userId, KakaoLoginType kakaoLoginType);

    void onFail(KakaoLoginError kakaoLoginError);

}

