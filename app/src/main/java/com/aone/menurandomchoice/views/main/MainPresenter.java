package com.aone.menurandomchoice.views.main;

import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginError;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLogoutListener;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;

public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter  {

    @Override
    public void handlingCustomerEnterButtonClick() {
        showProgressBarOfView();
        if(isAttachView()) {
            getView().moveToCustomerMainActivity();
        }
    }

    @Override
    public void handlingOwnerLoginButtonClick() {
        showProgressBarOfView();
        getRepository().checkLoggedinAccount(new OnKakaoLoginListener() {
            @Override
            public void onKakaoLoginSuccess(long userId) {
                requestSignedUpCheck(userId);
            }

            @Override
            public void onFail(@NonNull KakaoLoginError kakaoLoginError) {
                hideProgressBarOfView();
                handlingKakaoLoginFail();
            }
        });
    }

    private void requestSignedUpCheck(long userId) {
        getRepository().requestSignedUpCheck(userId, new NetworkResponseListener<LoginData>() {
            @Override
            public void onReceived(@NonNull LoginData loginData) {
                showProgressBarOfView();
                moveToOwnerStoreActivity(loginData);
            }

            @Override
            public void onError(JMTErrorCode errorCode) {
                showProgressBarOfView();
                requestKakaoAccountLogout();
            }
        });
    }

    private void requestKakaoAccountLogout() {
        getRepository().executeKakaoAccountLogout(new OnKakaoLogoutListener() {
            @Override
            public void onKakaoLogoutSuccess() {
                hideProgressBarOfView();
                moveToOwnerLoginActivity();
            }
        });
    }

    private void moveToOwnerStoreActivity(LoginData loginData) {
        if(isAttachView()) {
            getView().moveToOwnerStoreActivity(new UserAccessInfo(loginData.getStoreIdx(), true));
        }
    }

    private void handlingKakaoLoginFail() {
        if (isAttachView()) {
            getView().moveToOwnerLoginActivity();
        }
    }

    private void moveToOwnerLoginActivity() {
        if(isAttachView()) {
            getView().moveToOwnerLoginActivity();
        }
    }
}
