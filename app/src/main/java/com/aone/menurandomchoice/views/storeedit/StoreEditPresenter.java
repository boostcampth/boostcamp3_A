package com.aone.menurandomchoice.views.storeedit;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BasePresenter;

public class StoreEditPresenter extends BasePresenter<StoreEditContract.View> implements StoreEditContract.Presenter {


    @Override
    public void saveStoreDetail(StoreDetail storeDetail) {

        /*
        getRepository().saveStoreDetail(storeDetail, new onSaveStoreDetailListener() {
            @Override
            public void onSaved() {
                getView().showStoreSave();
            }
            @Override
            public void onFail() {
                getView().sho
            }
        });
        */
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
         getView().setStartTimePickerDialog(openTime);

    }

    @Override
    public void onEndTimeSetClick(String closeTime) {
        getView().setEndTimePickerDialog(closeTime);
    }

    @Override
    public void onTimeSet(String type, String hour, String minute) {
        if(type == "opentime")
            getView().showOpentimeChanged(hour, minute);
        else
            getView().showClosetimeChanged(hour, minute);
    }
}
