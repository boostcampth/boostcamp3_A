package com.aone.menurandomchoice.views.menuregister;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuregister.helper.UCropCreateHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yalantis.ucrop.UCrop;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

public class MenuRegisterPresenter extends BasePresenter<MenuRegisterContract.View>
        implements MenuRegisterContract.Presenter {

    @Override
    public void handlingImageRegisterButtonClick() {
        checkPermissionWithTedPermission();
    }

    @Override
    public void handlingPickPhotoResult(int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            handlingPickPhotoSuccess(data);
        } else {
            sendPhotoFailMessageToView();
        }
    }

    @Override
    public void handlingUCropResult(int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            handlingUCropSuccess(data);
        } else {
            sendPhotoFailMessageToView();
        }
    }

    private void checkPermissionWithTedPermission() {
        if(isAttachView()) {
            TedPermission.with(GlobalApplication.getGlobalApplicationContext())
                    .setRationaleMessage(GlobalApplication.getGlobalApplicationContext().getString(R.string.permission_request_guide))
                    .setDeniedMessage(GlobalApplication.getGlobalApplicationContext().getString(R.string.permission_denied_guide))
                    .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            openAlbumToView();
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                        }
                    })
                    .check();
        }
    }

    private void openAlbumToView() {
        if(isAttachView()) {
            getView().executePickPhotoFromAlbum();
        }
    }

    private void handlingPickPhotoSuccess(Intent data) {
        if(hasData(data)) {
            UCrop uCrop = createUCop(data.getData());
            sendUCropLibraryToView(uCrop);
        } else {
            sendPhotoFailMessageToView();
        }
    }

    private void handlingUCropSuccess(Intent data) {
        if(isAttachView()) {
            if (hasUCropData(data)) {
                getView().sendCropSuccessImageUri(UCrop.getOutput(data));
            } else {
                sendPhotoFailMessageToView();
            }
        }
    }

    private void sendPhotoFailMessageToView() {
        if(isAttachView()) {
            String message = GlobalApplication
                    .getGlobalApplicationContext()
                    .getString(R.string.activity_menu_register_photo_fail);

            getView().showToastMessage(message);
        }
    }

    private boolean hasData(Intent data) {
        return data != null && data.getData() != null;
    }

    private boolean hasUCropData(Intent data) {
        return data != null && UCrop.getOutput(data) != null;
    }

    private void sendUCropLibraryToView(UCrop uCrop) {
        if(isAttachView()) {
            getView().startToUCropLibrary(uCrop);
        }
    }

    private UCrop createUCop(Uri uri) {
        String destinationFileName = DateFormat.getDateInstance().format(new Date()) + ".jpg";
        float widthRatio = 9;
        float heightRatio = 16;
        int[] registerTargetImageSize = getView().getRegisterTargetImageSize();

        UCropCreateHelper uCropCreateHelper = new UCropCreateHelper();
        return uCropCreateHelper.createUCop(uri,
                destinationFileName,
                widthRatio, heightRatio,
                registerTargetImageSize[0],
                registerTargetImageSize[1]);
    }

}
