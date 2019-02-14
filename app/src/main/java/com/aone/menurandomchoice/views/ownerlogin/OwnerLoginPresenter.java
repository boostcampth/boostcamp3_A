package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Intent;
import android.content.res.Resources;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginError;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginType;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.server.OnSignedUpCheckListener;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;
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
        getRepository().requestSignedUpCheck(userId, new OnSignedUpCheckListener() {
            @Override
            public void onAlreadySignedUp() {
                moveToOwnerDetailActivity(userId);
            }

            @Override
            public void onNotSignUp() {
                if (kakaoLoginType != KakaoLoginType.LOGGEDIN) {
                    moveToSignUpActivity(userId);
                }
            }
        });
    }

    private void moveToOwnerDetailActivity(long userId) {
        if(isAttachView()) {
            getView().moveToOwnerStoreActivity(userId);
        }
    }

    private void moveToSignUpActivity(long userId) {
        if(isAttachView()) {
            getView().moveToSignUpActivity(userId);
        }
    }

    private void handlingKakaoLoginError(KakaoLoginError kakaoLoginError) {
        if(isAttachView()) {
            String errorMessage;
            try {
                int errorStringResourceId = kakaoLoginError.getStringResourceId();
                errorMessage = GlobalApplication.getGlobalApplicationContext().getString(errorStringResourceId);
            } catch (Resources.NotFoundException e) {
                errorMessage = GlobalApplication.getGlobalApplicationContext().getString(R.string.activity_owner_toast_system_error);
            }

            getView().showToastMessage(errorMessage);
        }
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
