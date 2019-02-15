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
    public void onAddressClick() {
    }

    @Override
    public void onMapClick() {
    }

    @Override
    public void onMenuEditClick(MenuDetail menuDetail) {
    }
}
