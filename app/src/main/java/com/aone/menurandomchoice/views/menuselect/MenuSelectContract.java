package com.aone.menurandomchoice.views.menuselect;

import com.aone.menurandomchoice.views.base.BaseContract;

public interface MenuSelectContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter<MenuSelectContract.View> {

        void handlingMenuSelectButtonClick();

    }

}
