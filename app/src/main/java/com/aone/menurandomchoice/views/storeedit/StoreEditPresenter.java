package com.aone.menurandomchoice.views.storeedit;

import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.EmptyObject;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.utils.ClickUtil;
import com.aone.menurandomchoice.views.base.BasePresenter;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StoreEditPresenter extends BasePresenter<StoreEditContract.View> implements StoreEditContract.Presenter {

    @Override
    public void handlingReceivedStoreDetail(@Nullable StoreDetail storeDetail) {
        if(storeDetail != null) {
            sendStoreDetailToView(storeDetail);
            handlingReceivedMapInfo(storeDetail.getAddress(),
                    storeDetail.getLatitude(),
                    storeDetail.getLongitude());
        } else {
            sendMessageToView(R.string.activity_store_edit_store_info_received_fail);
            viewFinish();
        }
    }

    @Override
    public void saveStoreDetail(@Nullable StoreDetail storeDetail) {
        if(storeDetail != null) {
            if(isAttachView()) {
                showProgressBarOfView();
                getRepository().requestSaveStoreDetail(storeDetail, new NetworkResponseListener<EmptyObject>() {
                    @Override
                    public void onReceived(@NonNull EmptyObject response) {
                        if(isAttachView()) {
                            hideProgressBarOfView();
                            sendMessageToView(R.string.activity_store_edit_save_success);
                            viewFinish();
                        }
                    }

                    @Override
                    public void onError(JMTErrorCode errorCode) {
                        hideProgressBarOfView();
                        sendMessageToView(errorCode.getStringResourceId());
                    }
                });
            }
        } else {
            hideProgressBarOfView();
            sendMessageToView(R.string.activity_store_edit_store_info_received_fail);
            viewFinish();
        }
    }

    @Override
    public void onLocationSearchClick() {
        if(isAttachView()) {
            getView().moveToLocationSearchPage();
        }
    }

    @Override
    public void onMenuEditClick(View view, MenuDetail menuDetail) {
        ClickUtil.preventDuplicateClick(view);

        if(isAttachView()) {
            getView().moveToMenuEditPage(menuDetail);
        }
    }

    @Override
    public void onStartTimeSetClick(String openTime) {
        if(isAttachView()) {
            getView().showStartTimePickerDialog(openTime);
        }
    }

    @Override
    public void onEndTimeSetClick(String closeTime) {
        if(isAttachView()) {
            getView().showEndTimePickerDialog(closeTime);
        }
    }

    @Override
    public void onTimeSet(int type, @NonNull String hour, @NonNull String minute) {
        if(isAttachView()) {
            if (type == TimePickerFragment.OPEN) {
                getView().showChangedOpenTime(hour + ":" + minute);
            } else {
                getView().showChangedCloseTime(hour + ":" + minute);
            }
        }
    }

    @Override
    public void handlingReceivedMenuDetailData(@Nullable MenuDetail menuDetail) {
        if(isAttachView()) {
            if(menuDetail != null) {
                int menuDetailIndex = menuDetail.getSequence() - 1;
                StoreDetail storeDetail = getView().getInputtedStoreDetail();
                if(storeDetail != null) {
                    storeDetail.getMenuList().set(menuDetailIndex, menuDetail);
                    getView().showStoreDetailInfo(storeDetail);
                } else {
                    //todo error handling
                }
            } else {
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
            getView().setMapLatLon(latitude, longitude);
            getView().moveCenterToMap(mapPoint);
            getView().showStoreMakerToMap(marker);
        }
    }

    private void sendStoreDetailToView(StoreDetail storeDetail) {
        if(isAttachView()) {
            getView().showStoreDetailInfo(storeDetail);
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

    private void viewFinish() {
        if(isAttachView()) {
            getView().viewFinish();
        }
    }

}
