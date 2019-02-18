package com.aone.menurandomchoice.views.storeedit;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity.DEFAULT_LATITUDE;
import static com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity.DEFAULT_LONGITUDE;

public class StoreEditPresenter extends BasePresenter<StoreEditContract.View> implements StoreEditContract.Presenter {

    @Override
    public void handlingReceivedStoreDetail(@Nullable StoreDetail storeDetail) {
        if(storeDetail != null) {
            if(isAttachView()) {
                getView().showStoreDetailInfo(storeDetail);
                handlingReceivedMapInfo(storeDetail.getAddress(), storeDetail.getLatitude(), storeDetail.getLongitude());
            }
        } else {

        }
    }

    @Override
    public void saveStoreDetail(@Nullable StoreDetail storeDetail) {
        if(storeDetail != null) {
            if(isAttachView()) {
                storeDetail.setName(getView().getInputtedStoreName());
                storeDetail.setDescription(getView().getInputtedDescription());
            }
        } else {

        }
    }

    @Override
    public void onLocationSearchClick() {
        getView().moveToLocationSearchPage();
    }

    @Override
    public void onMenuEditClick(MenuDetail menuDetail) {
        getView().moveToMenuEditPage(menuDetail);
    }

    @Override
    public void onStartTimeSetClick(String openTime) {
         getView().showStartTimePickerDialog(openTime);

    }

    @Override
    public void onEndTimeSetClick(String closeTime) {
        getView().showEndTimePickerDialog(closeTime);
    }

    @Override
    public void onTimeSet(@NonNull String type, @NonNull String hour, @NonNull String minute) {
        if(isAttachView()) {
            if (type.equals("opentime")) {
                getView().showChangedOpenTime(hour + ":" +minute);
            } else {
                getView().showChangedCloseTime(hour + ":" + minute);
            }
        }
    }

    @Override
    public void handlingReceivedMenuDetailData(@Nullable MenuDetail menuDetail) {
        if(menuDetail != null) {
            if(isAttachView()) {
                getView().setMenuDetailToDataBinding(menuDetail);
            }
        } else {
            if(isAttachView()) {
                //todo error handling
            }
        }
    }

    @Override
    public void handlingReceivedMapInfo(@NonNull String address, double latitude, double longitude) {
        if(isAttachView()) {
            MapPoint mapPoint = createMapPoint(latitude, longitude);
            MapPOIItem marker = createMaker(mapPoint);

            getView().setMapAddress(address);
            getView().moveCenterToMap(mapPoint);
            getView().showStoreMakerToMap(marker);
        }
    }

    private MapPoint createMapPoint(double latitude, double longitude) {
        final double DEFAULT_LATITUDE = 37.5514579595;
        final double DEFAULT_LONGITUDE = 126.951949155;

        if(latitude == 0 && longitude == 0) {
            latitude = DEFAULT_LATITUDE;
            longitude = DEFAULT_LONGITUDE;
        }

        return MapPoint.mapPointWithGeoCoord(latitude, longitude);
    }

    private MapPOIItem createMaker(MapPoint mapPoint) {
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);

        return marker;
    }
}
