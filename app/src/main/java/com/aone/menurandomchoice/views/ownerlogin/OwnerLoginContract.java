package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Intent;

import com.aone.menurandomchoice.views.base.BaseContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface OwnerLoginContract {

    interface View extends BaseContract.View {

        void moveToOwnerDetailActivity(long userId);

        void moveToSignUpActivity(long userId);

        void showToastMessage(@NonNull String message);

    }

    interface Presenter extends BaseContract.Presenter<OwnerLoginContract.View> {

        void handlingLoggedInAccount();

        void handlingDeviceKaKaoAccountLogin();

        void handlingOtherKaKaoAccountLogin();

        boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data);

    }

}
