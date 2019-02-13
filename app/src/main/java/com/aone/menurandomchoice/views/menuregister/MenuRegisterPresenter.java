package com.aone.menurandomchoice.views.menuregister;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import com.aone.menurandomchoice.views.menuregister.helper.UCropCreateHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

public class MenuRegisterPresenter extends BasePresenter<MenuRegisterContract.View>
        implements MenuRegisterContract.Presenter {

    private MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel;
    private UCropCreateHelper uCropCreateHelper;

    MenuRegisterPresenter() {
        setUp();
    }

    private void setUp() {
        uCropCreateHelper = new UCropCreateHelper();
    }

    @Override
    public void setAdapterModel(@NonNull MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel) {
        this.menuCategoryAdapterModel = menuCategoryAdapterModel;
        this.menuCategoryAdapterModel.setItems(createMenuCategoryItems());
        this.menuCategoryAdapterModel.selectDefaultCategory();
    }

    @Override
    public void handlingMenuCategoryItemClick(int position) {
        menuCategoryAdapterModel.setSelectedPositionOfMenuCategories(position);
    }

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

    @Override
    public void handlingPreviewButtonClick() {
        if(isAttachView()) {
            MenuDetail menuDetail = createMenuDetailItem();
            RegisterState registerState = checkMenuDetailItem(menuDetail);
            if(registerState == RegisterState.SUCCESS) {
                getView().moveToPreviewActivityWithItem(menuDetail);
            } else {
                sendRegisterErrorMessage(registerState);
            }
        }
    }

    @Override
    public void handlingRegisterOkButtonClick() {
        if(isAttachView()) {
            MenuDetail menuDetail = createMenuDetailItem();
            getRepository().clearRegisteredImageLocalPath();
            RegisterState registerState = checkMenuDetailItem(menuDetail);
            if(registerState == RegisterState.SUCCESS) {
                getView().moveToPreviousActivityWithItem(menuDetail);
            } else {
                sendRegisterErrorMessage(registerState);
            }
        }
    }

    @Override
    public void handlingBackKeyClick() {
        getRepository().clearRegisteredImageLocalPath();
        if(isAttachView()) {
            getView().finishView();
        }
    }

    private void checkPermissionWithTedPermission() {
        if(isAttachView()) {
            TedPermission.with(getView().getAppContext())
                    .setRationaleMessage(getView().getAppContext().getString(R.string.permission_request_guide))
                    .setDeniedMessage(getView().getAppContext().getString(R.string.permission_denied_guide))
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

    private List<MenuCategoryItem> createMenuCategoryItems() {
        ArrayList<MenuCategoryItem> menuCategoryItems = new ArrayList<>();
        String[] categories = getView().getAppContext().getResources().getStringArray(R.array.category);

        for(String category : categories) {
            menuCategoryItems.add(new MenuCategoryItem(category));
        }

        return menuCategoryItems;
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
        if (hasUCropData(data)) {
            Uri uri = UCrop.getOutput(data);
            if(uri != null) {
                String imagePath = uri.getPath();
                if (imagePath != null) {
                    saveRegisteredImageLocalPath(imagePath);
                    sendRegisteredImageUriToView(imagePath);
                } else {
                    sendPhotoFailMessageToView();
                }
            } else {
                sendPhotoFailMessageToView();
            }
        } else {
            sendPhotoFailMessageToView();
        }
    }

    private void sendPhotoFailMessageToView() {
        if(isAttachView()) {
            String message = getView().getAppContext().getString(R.string.activity_menu_register_photo_fail);
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
        final float X_RATIO = 9;
        final float Y_RATIO = 16;
        int[] viewSize = getView().getRegisterTargetImageSize();

        return uCropCreateHelper.createUCop(uri, X_RATIO, Y_RATIO, viewSize[0], viewSize[1]);
    }

    private void saveRegisteredImageLocalPath(String imagePath) {
        getRepository().saveRegisteredImageLocalPath(imagePath);
    }

    private void sendRegisteredImageUriToView(String imagePath) {
        getView().showRegisteredImage(imagePath);
    }

    private MenuDetail createMenuDetailItem() {
        MenuDetail menuDetail = new MenuDetail();

        menuDetail.setPhotoUrl(getRepository().getSavedRegisterImageLoadPath());
        menuDetail.setName(getView().getInputtedMenuName());
        menuDetail.setDescription(getView().getInputtedMenuDescription());

        String price = getView().getInputtedMenuPrice();
        if(price.length() < 1) {
            menuDetail.setPrice(0);
        } else {
            menuDetail.setPrice(Integer.parseInt(price));
        }

        if(menuCategoryAdapterModel != null) {
            menuDetail.setCategory(menuCategoryAdapterModel.getSelectedCategory());
        }

        return menuDetail;
    }

    private RegisterState checkMenuDetailItem(MenuDetail menuDetail) {
        if(menuDetail.getPhotoUrl().length() < 1) {
            return RegisterState.NO_IMAGE;
        }

        if(menuDetail.getName().length() < 1) {
            return RegisterState.NO_NAME;
        }

        if(menuDetail.getDescription().length() < 1) {
            return RegisterState.NO_DESCRIPTION;
        }

        if(menuDetail.getPrice() == 0) {
            return RegisterState.NO_PRICE;
        }

        if(menuDetail.getCategory().length() < 1) {
            return RegisterState.NO_CATEGORY;
        }

        return RegisterState.SUCCESS;
    }

    private void sendRegisterErrorMessage(RegisterState registerState) {
        if(isAttachView()) {
            int errorMessageId = registerState.getMessageResourceId();
            String message = getView().getAppContext().getResources().getString(errorMessageId);
            getView().showToastMessage(message);
        }
    }

}
