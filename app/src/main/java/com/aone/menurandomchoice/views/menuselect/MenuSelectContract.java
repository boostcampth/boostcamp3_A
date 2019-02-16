package com.aone.menurandomchoice.views.menuselect;

import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseContract;
import com.aone.menurandomchoice.views.menuselect.adapter.MenuSelectOverlapViewAdapterContract;

import androidx.annotation.NonNull;

public interface MenuSelectContract {

    interface View extends BaseContract.View {

        void moveToOwnerStoreActivity(UserAccessInfo userAccessInfo);

    }

    interface Presenter extends BaseContract.Presenter<MenuSelectContract.View> {

        void requestMenuList();

        void setAdapterModel(@NonNull MenuSelectOverlapViewAdapterContract.Model adapterModel);

        void handlingMenuSelectButtonClick();

    }

}
