package com.aone.menurandomchoice.views.customermain;

import android.view.View;

import com.aone.menurandomchoice.views.base.BaseContract;

public interface CustomerMainContract {

    interface View extends BaseContract.View{

        void moveToLocationSearchPage();

        void moveToMenuSelectPage();

        void onRadiusButtonClicked(android.view.View view);
    }

    interface Presenter extends BaseContract.Presenter<CustomerMainContract.View> {

        void requestMenuList(double latitude, double longitude);

        void stopNetwork();
    }

}
