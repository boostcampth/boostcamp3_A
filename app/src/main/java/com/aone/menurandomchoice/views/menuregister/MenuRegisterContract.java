package com.aone.menurandomchoice.views.menuregister;

import android.content.Intent;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.views.base.BaseContract;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import com.yalantis.ucrop.UCrop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface MenuRegisterContract {

    interface View extends BaseContract.View {

        void executePickPhotoFromAlbum();

        void startToUCropLibrary(UCrop uCrop);

        void showRegisteredImage(String imagePath);

        void moveToPreviewActivityWithItem(MenuDetail menuDetail);

        void moveToPreviousActivityWithItem(MenuDetail menuDetail);

        void finishView();

        @NonNull
        int[] getRegisterTargetImageSize();

        @NonNull
        String getInputtedMenuName();

        @NonNull
        String getInputtedMenuDescription();

        @NonNull
        String getInputtedMenuPrice();

    }

    interface Presenter extends BaseContract.Presenter<MenuRegisterContract.View> {

        void setAdapterModel(@NonNull MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel);

        void handlingMenuCategoryItemClick(int position);

        void handlingImageRegisterButtonClick();

        void handlingPickPhotoResult(int resultCode, @Nullable Intent data);

        void handlingUCropResult(int resultCode, @Nullable Intent data);

        void handlingPreviewButtonClick();

        void handlingRegisterOkButtonClick();

        void handlingBackKeyClick();

    }

}
