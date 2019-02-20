package com.aone.menurandomchoice.views.storelocation;

import com.aone.menurandomchoice.views.base.BaseContract;

public interface StoreLocationContract {
    interface View extends BaseContract.View {

        void onConfirmButtonClicked();

    }

    interface Presenter extends BaseContract.Presenter<StoreLocationContract.View> {

    }

}
