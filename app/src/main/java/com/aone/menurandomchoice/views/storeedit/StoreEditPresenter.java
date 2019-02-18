package com.aone.menurandomchoice.views.storeedit;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.Nullable;

public class StoreEditPresenter extends BasePresenter<StoreEditContract.View> implements StoreEditContract.Presenter {


    @Override
    public void saveStoreDetail(StoreDetail storeDetail) {
        //Todo . retrofit
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
        if(type.equals("opentime")) {
            getView().showOpentimeChanged(hour, minute);
        } else {
            getView().showClosetimeChanged(hour, minute);
        }
    }

    @Override
    public void handlingReceivedMenuDetailData(@Nullable MenuDetail menuDetail) {
        if(menuDetail != null) {
//            getRepository().saveRegisterMenuInfo(menuDetail);
            if(isAttachView()) {
                getView().setMenuDetailToDataBinding(menuDetail);
            }
        } else {
            if(isAttachView()) {
                //todo error handling
            }
        }
    }

}
