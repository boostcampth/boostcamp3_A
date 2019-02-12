package com.aone.menurandomchoice.views.menuregister;

import com.aone.menurandomchoice.views.base.BaseContract;

public interface MenuRegisterContract {

    interface View extends BaseContract.View {

        void checkPermission();

        void openAlbumOfDevice();

    }

    interface Presenter extends BaseContract.Presenter<MenuRegisterContract.View> {

        void handlingImageRegisterButtonClick();

        void handlingImageRegisterPermissionGranted();

    }

}
