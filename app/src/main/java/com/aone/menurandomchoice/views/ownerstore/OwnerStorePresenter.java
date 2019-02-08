package com.aone.menurandomchoice.views.ownerstore;

import com.aone.menurandomchoice.repository.Repository;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BasePresenter;

public class OwnerStorePresenter extends BasePresenter<OwnerStoreContract.View> implements  OwnerStoreContract.Presenter {

    @Override
    public void loadStoreDetail(int storeIdx) {

        getRepository().loadStoreDetail(storeIdx, new Repository.OnLoadStoreDetailListener() {
            @Override
            public void onStoreDetailLoaded(StoreDetail storeDetail) {

                if (!isAttachView()) {
                    return;
                }

                
            }

            @Override
            public void onFailToLoadStoreDetail() {

                if (!isAttachView()) {
                    return;
                }

                //토스트메시지 띄우기
            }
        });
    }


    @Override
    public void stopNetwork() {
        getRepository().cancelAll();
    }


}
