package com.aone.menurandomchoice.views.menuregister;

import com.aone.menurandomchoice.views.base.BaseContract;

public interface MenuRegisterContract {

    interface View extends BaseContract.View {

        void moveToImageRegisterActivity();

    }

    interface Presenter extends BaseContract.Presenter<MenuRegisterContract.View> {

        void onClickImageRegisterButton();
    }

}
