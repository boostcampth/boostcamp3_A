package com.aone.menurandomchoice.views.customermain;

import android.view.View;

import com.aone.menurandomchoice.views.base.BaseContract;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;

import androidx.annotation.NonNull;

public interface CustomerMainContract {

    interface View extends BaseContract.View{

        void moveToLocationSearchPage();

        void moveToMenuSelectPage();

        void onRadiusButtonClicked(android.view.View view);
    }

    interface Presenter extends BaseContract.Presenter<CustomerMainContract.View> {

        void requestMenuList(double latitude, double longitude);

        void stopNetwork();

        void setAdapterModel(@NonNull MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel);

        void handlingMenuCategoryItemClick(int position);
    }

}
