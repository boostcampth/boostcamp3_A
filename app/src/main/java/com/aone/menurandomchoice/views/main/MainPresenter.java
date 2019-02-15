package com.aone.menurandomchoice.views.main;

import android.util.Log;

import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginError;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginType;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLogoutListener;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;

public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter  {

    @Override
    public void handlingCustomerEnterButtonClick() {
        if(isAttachView()) {
            getView().moveToCustomerMainActivity();
        }
    }

    @Override
    public void handlingOwnerLoginButtonClick() {
        getRepository().checkLoggedinAccount(new OnKakaoLoginListener() {
            @Override
            public void onKakaoLoginSuccess(long userId, @NonNull KakaoLoginType kakaoLoginType) {
                requestSignedUpCheck(userId);
            }

            @Override
            public void onFail(@NonNull KakaoLoginError kakaoLoginError) {
                if(isAttachView()) {
                    getView().moveToOwnerLoginActivity();
                }
            }
        });
    }

    private void requestSignedUpCheck(long userId) {
        getRepository().requestSignedUpCheck(userId, new NetworkResponseListener<LoginData>() {
            @Override
            public void onReceived(@NonNull LoginData loginData) {
                handlingAlreadySignedUp(loginData);
            }

            @Override
            public void onError() {
                requestKakaoAccountLogout();
            }
        });
    }

    private void requestKakaoAccountLogout() {
        getRepository().executeKakaoAccountLogout(new OnKakaoLogoutListener() {
            @Override
            public void onKakaoLogoutSuccess() {
                if(isAttachView()) {
                    getView().moveToOwnerLoginActivity();
                }
            }
        });
    }

    private void handlingAlreadySignedUp(LoginData loginData) {
        if(isAttachView()) {
            int storeIndex = loginData.getStoreIdx();
            boolean isOwner = true;

            getView().moveToOwnerStoreActivity(new UserAccessInfo(storeIndex, isOwner));
        }
    }
}
