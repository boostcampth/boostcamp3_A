package com.aone.menurandomchoice.views.menuselect;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuselect.adapter.MenuSelectOverlapViewAdapterContract;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MenuSelectPresenter extends BasePresenter<MenuSelectContract.View>
    implements MenuSelectContract.Presenter {

    private MenuSelectOverlapViewAdapterContract.Model adapterModel;

    @Override
    public void requestMenuList(@Nullable MenuSearchRequest menuSearchRequest) {
        menuSearchRequest = new MenuSearchRequest(111,111,111,"DD");

        if(menuSearchRequest != null) {
            requestMenuDetailListToRepository(menuSearchRequest);
        } else {
            sendErrorMessageToView(R.string.item_menu_select_system_error);
            getView().finish();
        }
    }

    @Override
    public void setAdapterModel(@NonNull MenuSelectOverlapViewAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void handlingMenuSelectButtonClick() {
        UserAccessInfo userAccessInfo = getUserAccessInfo();
        if(userAccessInfo != null) {
            moveToOwnerStoreActivity(userAccessInfo);
        } else {
            sendErrorMessageToView(R.string.item_menu_select_error);
        }
    }

    private void requestMenuDetailListToRepository(MenuSearchRequest menuSearchRequest) {
        getRepository().requestMenuList(menuSearchRequest, new NetworkResponseListener<List<MenuDetail>>() {
            @Override
            public void onReceived(@NonNull List<MenuDetail> menuDetailList) {
                if(adapterModel != null) {
                    adapterModel.setItemList(menuDetailList);
                }
            }

            @Override
            public void onError(JMTErrorCode errorCode) {
                sendErrorMessageToView(errorCode.getStringResourceId());
                getView().finish();
            }
        });
    }

    private UserAccessInfo getUserAccessInfo() {
        UserAccessInfo userAccessInfo = null;
        if(adapterModel != null) {
            MenuDetail menuDetail = adapterModel.getTopViewData();
            if(menuDetail != null) {
                int storeIdx = menuDetail.getStoreIdx();
                userAccessInfo = new UserAccessInfo(storeIdx, false);
            }
        }

        return userAccessInfo;
    }

    private void moveToOwnerStoreActivity(UserAccessInfo userAccessInfo) {
        if(isAttachView()) {
            getView().moveToOwnerStoreActivity(userAccessInfo);
        }
    }

    private void sendErrorMessageToView(int stringResourceId) {
        if(isAttachView()) {
            String errorMessage = GlobalApplication.getGlobalApplicationContext().getString(stringResourceId);
            getView().showToastMessage(errorMessage);
        }
    }

}
