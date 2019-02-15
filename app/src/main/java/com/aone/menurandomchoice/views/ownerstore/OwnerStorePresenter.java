package com.aone.menurandomchoice.views.ownerstore;

import com.aone.menurandomchoice.repository.Repository;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BasePresenter;

public class OwnerStorePresenter extends BasePresenter<OwnerStoreContract.View> implements  OwnerStoreContract.Presenter {

    @Override
    public void loadStoreDetail(int storeIdx) {

        getRepository().loadStoreDetail(50, new Repository.OnLoadStoreDetailListener() {
            @Override
            public void onStoreDetailLoaded(StoreDetail storeDetail) {

                if (isAttachView()) {
                    getView().showStoreDetail(storeDetail);
                }
            }

            @Override
            public void onFailToLoadStoreDetail(StoreDetail cachedStoreDetail, String errorMessage) {

                if (isAttachView()) {
                    getView().showErrorStoreDetail(cachedStoreDetail, errorMessage);
                }
            }
        });
    }

    @Override
    public void onMenuDetailClick(MenuDetail menuDetail) {
        getView().moveToMenuDetailPage(menuDetail);
    }

    @Override
    public void onMapClick(double latitude, double longitude) {
        getView().moveToMapDetailPage(latitude, longitude);
    }

    @Override
    public void stopNetwork() {
        getRepository().cancelAll();
    }

}
