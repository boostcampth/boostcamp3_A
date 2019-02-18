package com.aone.menurandomchoice.views.storeedit;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BaseContract;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface StoreEditContract {

    interface View extends BaseContract.View {

        void showStoreDetailInfo(@NonNull StoreDetail storeDetail);

        void moveToMenuEditPage(MenuDetail menuDetail);

        void moveToLocationSearchPage();

        void showStartTimePickerDialog(@NonNull String startTime);

        void showEndTimePickerDialog(@NonNull String endTime);

        void showChangedOpenTime(@NonNull String openTime);

        void showChangedCloseTime(@NonNull String closeTime);

        void setMenuDetailToDataBinding(@NonNull MenuDetail menuDetail);

        @NonNull
        String getInputtedStoreName();

        @NonNull
        String getInputtedDescription();

        void moveCenterToMap(@NonNull MapPoint centerPoint);

        void showStoreMakerToMap(@NonNull MapPOIItem marker);

        void setMapAddress(@NonNull String address);

        void viewFinish();

    }

    interface Presenter extends BaseContract.Presenter<StoreEditContract.View> {

        void handlingReceivedStoreDetail(@Nullable StoreDetail storeDetail);

        void onStartTimeSetClick(String time);

        void onEndTimeSetClick(String time);

        void onTimeSet(@NonNull String type, @NonNull String hour, @NonNull String minute);

        void onLocationSearchClick();

        void onMenuEditClick(MenuDetail menuDetail);

        void saveStoreDetail(@Nullable StoreDetail storeDetail);

        void handlingReceivedMenuDetailData(@Nullable MenuDetail menuDetail);

        void handlingReceivedMapInfo(@NonNull String address, double latitude, double longitude);

    }
}
