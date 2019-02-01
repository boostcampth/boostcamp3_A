package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Intent;

import com.aone.menurandomchoice.repository.oauth.KakaoLoginError;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginType;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.server.OnSignedUpCheckListener;
import com.aone.menurandomchoice.views.base.BasePresenter;


import androidx.annotation.Nullable;

public class OwnerLoginPresenter extends BasePresenter<OwnerLoginContract.View>
        implements OwnerLoginContract.Presenter {

    @Override
    public void handlingLoggedInAccount() {
        getRepository().checkLoggedinAccount(onKakaoLoginListener);
    }

    @Override
    public void handlingDeviceKaKaoAccountLogin() {
        getRepository().executeDeviceKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public void handlingOtherKaKaoAccountLogin() {
        getRepository().executeOtherKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data) {
        return getRepository().isNeedKakaoSDKLoginScreen(requestCode, resultCode, data);
    }

    private void requestSignUpCheck(final long userId, final KakaoLoginType kakaoLoginType) {
        getRepository().requestSignUpCheck(userId, new OnSignedUpCheckListener() {
            @Override
            public void onAlreadySignUp() {
                getView().moveToOwnerDetailActivity(userId);
            }

            @Override
            public void onNotSignUp() {
                if(kakaoLoginType != KakaoLoginType.LOGGEDIN) {
                    getView().moveToSignUpActivity(userId);
                }
            }
        });
    }

    private void handlingKakaoLoginError(KakaoLoginError kakaoLoginError) {
        switch (kakaoLoginError) {

        }

    }

    private OnKakaoLoginListener onKakaoLoginListener = new OnKakaoLoginListener() {

        @Override
        public void onKakaoLoginSuccess(long userId, KakaoLoginType kakaoLoginType) {
            requestSignUpCheck(userId, kakaoLoginType);
        }

        @Override
        public void onFail(KakaoLoginError kakaoLoginError) {
            handlingKakaoLoginError(kakaoLoginError);
        }

    };

}
