package com.aone.menurandomchoice.views.ownerstore;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;

public class OwnerStorePresenter extends BasePresenter<OwnerStoreContract.View> implements  OwnerStoreContract.Presenter {

    @Override
    public void loadStoreDetail(int storeIdx) {
        getRepository().loadStoreDetail(50, new NetworkResponseListener<StoreDetail>() {
            @Override
            public void onReceived(@NonNull StoreDetail storeDetail) {
                if (isAttachView()) {
                    getView().showStoreDetail(storeDetail);
                }
            }

            @Override
            public void onError() {
                if (isAttachView()) {
                    getView().showToastMessage("서버에러");
                }
            }
        });
    }

    @Override
    public void onMenuDetailClick(MenuDetail menuDetail) {
        if(menuDetail.getPhotoUrl() == null)
            return;
        getView().moveToMenuPreviewPage(menuDetail);
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
