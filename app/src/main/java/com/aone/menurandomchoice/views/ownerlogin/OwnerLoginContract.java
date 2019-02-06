package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Intent;

import com.aone.menurandomchoice.views.base.BaseContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface OwnerLoginContract {

    interface View extends BaseContract.View {

        void moveToOwnerDetailActivity(long userId);

        void moveToSignUpActivity(long userId);

        // FIXME 여러 Activity에서 토스트를 사용하는데 BaseContract.View에 정의해두면 더 좋을것 같습니다.
        void showToastMessage(@NonNull String message);

    }

    interface Presenter extends BaseContract.Presenter<OwnerLoginContract.View> {

        void handlingLoggedInAccount();

        void handlingDeviceKaKaoAccountLogin();

        void handlingOtherKaKaoAccountLogin();

        boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data);

    }

}
