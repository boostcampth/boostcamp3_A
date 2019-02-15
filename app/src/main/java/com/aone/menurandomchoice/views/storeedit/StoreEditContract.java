package com.aone.menurandomchoice.views.storeedit;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BaseContract;

public interface StoreEditContract {
    interface View extends BaseContract.View {
        void moveToMenuEditPage();

        void moveToLocationSearchPage();

    }

    interface Presenter extends BaseContract.Presenter<StoreEditContract.View> {
        void onMapClick();

        void onAddressClick();

        void onMenuEditClick(MenuDetail menuDetail);

        void saveStoreDetail(StoreDetail storeDetail);
    }
}
