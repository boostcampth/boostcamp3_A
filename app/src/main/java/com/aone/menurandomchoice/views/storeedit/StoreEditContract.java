package com.aone.menurandomchoice.views.storeedit;

import com.aone.menurandomchoice.repository.pojo.MenuDetail;
import com.aone.menurandomchoice.views.base.BaseContract;

public interface StoreEditContract {
    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter<StoreEditContract.View> {
        void onMapClick();

        void onAddressClick();

        void onMenuEditClick(MenuDetail menuDetail);
    }
}
