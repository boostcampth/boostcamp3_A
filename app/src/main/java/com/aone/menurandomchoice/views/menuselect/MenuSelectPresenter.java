package com.aone.menurandomchoice.views.menuselect;

import android.text.TextUtils;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuselect.adapter.MenuSelectOverlapViewAdapterContract;

import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MenuSelectPresenter extends BasePresenter<MenuSelectContract.View>
    implements MenuSelectContract.Presenter {

    private MenuSelectOverlapViewAdapterContract.Model adapterModel;

    @Override
    public void requestMenuList(@Nullable MenuSearchRequest menuSearchRequest) {
        if(menuSearchRequest != null) {
            showProgressBarOfView();
            filteringCategoryOfRequest(menuSearchRequest);
            requestMenuDetailListToRepository(menuSearchRequest);
        } else {
            hideProgressBarOfView();
            sendErrorMessageToView(R.string.item_menu_select_system_error);
            finishView();
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
                hideProgressBarOfView();
                if(adapterModel != null) {
                    if(menuDetailList.size() > 0 && TextUtils.isEmpty(menuDetailList.get(0).getCategory())) { // "" means both of '전체', 'no data'
                        Iterator<MenuDetail> it = menuDetailList.iterator();
                        while (it.hasNext()) {
                            MenuDetail value = it.next();
                            if (TextUtils.isEmpty(value.getPhotoUrl())) { // remove 'no data' by checking photoUrl
                                it.remove();
                            }
                        }
                    }
                    adapterModel.setItemList(menuDetailList);
                }
            }

            @Override
            public void onError(JMTErrorCode errorCode) {
                hideProgressBarOfView();
                sendErrorMessageToView(errorCode.getStringResourceId());
                finishView();
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

    private void sendErrorMessageToView(int resourceId) {
        if(isAttachView()) {
            String errorMessage = GlobalApplication.getGlobalApplicationContext().getString(resourceId);
            getView().showToastMessage(errorMessage);
        }
    }

    private void finishView() {
        if(isAttachView()) {
            getView().finish();
        }
    }

    private void filteringCategoryOfRequest(MenuSearchRequest menuSearchRequest) {
        String removeWord = GlobalApplication
                .getGlobalApplicationContext()
                .getString(R.string.arrays_category_all_food);

        if(removeWord.equals(menuSearchRequest.getCategory())) {
            menuSearchRequest.removeCategory();
        }
    }
}
