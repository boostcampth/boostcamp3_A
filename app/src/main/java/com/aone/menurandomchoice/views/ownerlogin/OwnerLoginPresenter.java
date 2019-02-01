package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Context;
import android.content.Intent;

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
            getView().moveToOwnerDetailActivity(userId);
        }
    }

    private void moveToSignUpActivity(long userId) {
        if(isAttachView()) {
            getView().moveToSignUpActivity(userId);
        }
    }

    private void handlingKakaoLoginError(KakaoLoginError kakaoLoginError) {
        Context context = GlobalApplication.getGlobalApplicationContext();
        switch (kakaoLoginError) {
            case SERVER_ERROR:
                getView().showToastMessage(context.getString(R.string.activity_owner_toast_server_error));
                break;
            case EXCEED_REQUEST_COUNT_ERROR:
                getView().showToastMessage(context.getString(R.string.activity_owner_toast_exceed_request_count_error));
                break;
            case AUTHORIZATION_FAIL_ERROR:
                getView().showToastMessage(context.getString(R.string.activity_owner_toast_authorization_fail_error));
                break;
            case SERVER_CHECK_ERROR:
                getView().showToastMessage(context.getString(R.string.activity_owner_toast_server_check_error));
                break;
            case SYSTEM_ERROR:
                getView().showToastMessage(context.getString(R.string.activity_owner_toast_system_error));
                break;
            case CANCELED_OPERATION_ERROR:
                getView().showToastMessage(context.getString(R.string.activity_owner_toast_canceled_operation_error));
                break;
            case NETWORK_NOT_CONNECT_ERROR:
                getView().showToastMessage(context.getString(R.string.activity_owner_toast_network_not_connect_error));
                break;
            case UNKNOWN_ERROR:
                getView().showToastMessage(context.getString(R.string.activity_owner_toast_unknown_error));
                break;
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
