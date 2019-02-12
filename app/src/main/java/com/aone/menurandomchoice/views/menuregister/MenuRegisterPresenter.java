package com.aone.menurandomchoice.views.menuregister;

import com.aone.menurandomchoice.views.base.BasePresenter;

public class MenuRegisterPresenter extends BasePresenter<MenuRegisterContract.View>
        implements MenuRegisterContract.Presenter {

    @Override
    public void handlingImageRegisterButtonClick() {
        if(isAttachView()) {
            getView().checkPermission();
        }
    }

    @Override
    public void handlingImageRegisterPermissionGranted() {
        if(isAttachView()) {
            getView().openAlbumOfDevice();
        }
    }
}
