package com.aone.menurandomchoice.views.menuregister;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.aone.menurandomchoice.views.base.BaseContract;
import com.yalantis.ucrop.UCrop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface MenuRegisterContract {

    interface View extends BaseContract.View {

        void executePickPhotoFromAlbum();

        void startToUCropLibrary(UCrop uCrop);

        void sendCropSuccessImageUri(Uri uri);

        @NonNull
        int[] getRegisterTargetImageSize();

    }

    interface Presenter extends BaseContract.Presenter<MenuRegisterContract.View> {

        void handlingImageRegisterButtonClick();

        void handlingPickPhotoResult(int resultCode, @Nullable Intent data);

        void handlingUCropResult(int resultCode, @Nullable Intent data);

    }

}
