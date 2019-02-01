package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Intent;
import android.opengl.Visibility;

import com.aone.menurandomchoice.repository.oauth.KakaoLoginError;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginType;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.server.OnSignedUpCheckListener;
import com.aone.menurandomchoice.views.base.BasePresenter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableByte;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

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
        getRepository().requestSignedUpCheck(userId, new OnSignedUpCheckListener() {
            @Override
            public void onAlreadySignedUp() {
                getView().moveToOwnerDetailActivity(userId);
            }

            @Override
            public void onNotSignUp() {
                switch (kakaoLoginType) {
                    case KAKAO_TALK:
                        getView().moveToSignUpActivity(userId);
                        break;
                    case KAKAO_ACCOUNT:
                        getView().moveToSignUpActivity(userId);
                        break;
                }
            }
        });
    }

    private void handlingKakaoLoginError(KakaoLoginError kakaoLoginError) {
        //when the server implementation complement, we must error handling logic implement.
    }

    private OnKakaoLoginListener onKakaoLoginListener = new OnKakaoLoginListener() {

        @Override
        public void onKakaoLoginSuccess(long userId, @NonNull KakaoLoginType kakaoLoginType) {
            requestSignUpCheck(userId, kakaoLoginType);
        }

        @Override
        public void onFail(@NonNull KakaoLoginError kakaoLoginError) {
            handlingKakaoLoginError(kakaoLoginError);
        }

    };

}
