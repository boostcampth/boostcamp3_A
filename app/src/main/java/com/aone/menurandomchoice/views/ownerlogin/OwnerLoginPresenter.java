package com.aone.menurandomchoice.views.ownerlogin;

import android.util.Log;

public class OwnerLoginPresenter implements OwnerLoginContract.Presenter {

    @Override
    public void attachView(OwnerLoginContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public boolean isAttachView() {
        return false;
    }

    @Override
    public void requestLoginCheck() {
        Log.d("OwnerLoginPresenter", "requestLoginCheck");

    }

    @Override
    public void requestKaKaoAccountLogin() {
        Log.d("OwnerLoginPresenter", "requestKaKaoAccountLogin");

    }

    @Override
    public void requestOtherKaKaoAccountLogin() {
        Log.d("OwnerLoginPresenter", "requestOtherKaKaoAccountLogin");

    }

}
