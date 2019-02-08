package com.aone.menurandomchoice.views.ownerstore;

import com.aone.menurandomchoice.views.base.BaseContract;

public interface OwnerStoreContract {

    interface View extends BaseContract.View {
        void showStoreDetail();

        void moveToMenuDetailPage();

        void moveToOwnerEditPage();
    }

    interface Presenter extends BaseContract.Presenter<OwnerStoreContract.View> {
        void loadStoreDetail(int storeIdx);

        void stopNetwork();
    }
}
