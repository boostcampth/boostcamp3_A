package com.aone.menurandomchoice.views.storeedit;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BaseContract;

public interface StoreEditContract {
    interface View extends BaseContract.View {
        void moveToMenuEditPage(MenuDetail menuDetail);

        void moveToLocationSearchPage();

        void setStartTimePickerDialog(String startTime);

        void setEndTimePickerDialog(String endTime);

        void setStoreLocation();

        void showOpentimeChanged(String hour, String minute);

        void showClosetimeChanged(String hour, String minute);
    }

    interface Presenter extends BaseContract.Presenter<StoreEditContract.View> {

        void onStartTimeSetClick(String time);

        void onEndTimeSetClick(String time);

        void onTimeSet(String type, String hour, String minute);

        void onLocationSearchClick();

        void onMenuEditClick(MenuDetail menuDetail);

        void saveStoreDetail(StoreDetail storeDetail);

    }
}
