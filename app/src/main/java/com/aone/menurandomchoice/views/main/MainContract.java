package com.aone.menurandomchoice.views.main;

import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseContract;

public interface MainContract {

    interface View extends BaseContract.View {

        void moveToCustomerMainActivity();

        void moveToOwnerLoginActivity();

        void moveToOwnerStoreActivity(UserAccessInfo userAccessInfo);

    }

    interface Presenter extends BaseContract.Presenter<MainContract.View> {

        void handlingCustomerEnterButtonClick();

        void handlingOwnerLoginButtonClick();

    }

}
