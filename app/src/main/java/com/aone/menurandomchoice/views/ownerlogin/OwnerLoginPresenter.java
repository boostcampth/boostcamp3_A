package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Intent;

import com.aone.menurandomchoice.data.DataRepository;
import com.aone.menurandomchoice.data.Repository;

import androidx.annotation.Nullable;

public class OwnerLoginPresenter implements OwnerLoginContract.Presenter {

    private OwnerLoginContract.View view;
    private Repository repository;

    OwnerLoginPresenter() {
        repository = DataRepository.getInstance();
    }

    @Override
    public void attachView(OwnerLoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public boolean isAttachView() {
        return view != null;
    }

    @Override
    public void handlingLoggedInAccount() {
        repository.checkLoggedinAccount();
    }

    @Override
    public void handlingDeviceKaKaoAccountLogin() {
        repository.executeDeviceKakaoAccountLogin();
    }

    @Override
    public void handlingOtherKaKaoAccountLogin() {
        repository.executeOtherKakaoAccountLogin();
    }

    @Override
    public boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data) {
        return repository.isNeedKakaoSDKLoginScreen(requestCode, resultCode, data);
    }

}
