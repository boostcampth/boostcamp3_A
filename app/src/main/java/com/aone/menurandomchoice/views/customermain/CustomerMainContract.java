package com.aone.menurandomchoice.views.customermain;

import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.views.base.BaseContract;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;

import java.util.List;

import androidx.annotation.NonNull;

public interface CustomerMainContract {

    interface View extends BaseContract.View{

        void moveToLocationSearchPage();

        void moveToMenuSelectPage();

        void onRadiusButtonClicked(android.view.View view);

        void onGPSButtonClicked();

        void setMarkerAtNewLocation(double lat, double lon, List<MenuLocation> closerDistanceList);

        void updateMapByRadiusCategory();
    }

    interface Presenter extends BaseContract.Presenter<CustomerMainContract.View> {

        void requestMenuList(double latitude, double longitude, double radius);

        String getSelectedCategory();

        void stopNetwork();

        void getMenuCountFiltered(double centerLat, double centerLon, double radius);

        void setAdapterModel(@NonNull MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel);

        void handlingMenuCategoryItemClick(int position);
    }

}
