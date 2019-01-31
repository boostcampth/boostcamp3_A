package com.aone.menurandomchoice.views.ownerlogin;

import com.aone.menurandomchoice.views.base.BaseContract;

public interface OwnerLoginContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter<OwnerLoginContract.View> {

        void requestLoginCheck();

        void requestKaKaoAccountLogin();

        void requestOtherKaKaoAccountLogin();

    }

}
