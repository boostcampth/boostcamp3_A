package com.aone.menurandomchoice.views.menuselect;

import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseContract;
import com.aone.menurandomchoice.views.menuselect.adapter.MenuSelectOverlapViewAdapterContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface MenuSelectContract {

    interface View extends BaseContract.View {

        void moveToOwnerStoreActivity(UserAccessInfo userAccessInfo);

        void finish();

    }

    interface Presenter extends BaseContract.Presenter<MenuSelectContract.View> {

        void requestMenuList(@Nullable MenuSearchRequest menuSearchRequest);

        void setAdapterModel(@NonNull MenuSelectOverlapViewAdapterContract.Model adapterModel);

        void handlingMenuSelectButtonClick();

    }

}
