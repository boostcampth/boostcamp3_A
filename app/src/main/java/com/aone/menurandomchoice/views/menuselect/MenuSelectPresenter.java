package com.aone.menurandomchoice.views.menuselect;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuselect.adapter.MenuSelectOverlapViewAdapterContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class MenuSelectPresenter extends BasePresenter<MenuSelectContract.View>
    implements MenuSelectContract.Presenter {

    private MenuSelectOverlapViewAdapterContract.Model adapterModel;

    @Override
    public void requestMenuList() {
        requestMenuDetailListToRepository();
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

    private void requestMenuDetailListToRepository() {
        List<MenuDetail> menuDetailList = new ArrayList<>();
        menuDetailList.add(new MenuDetail("김치말이국수", 15000, R.drawable.test1, "이거 완전맛있어용~!!! 먹어보세용~!!!! 두말하면 잔소리~!!", "한식", 0));
        menuDetailList.add(new MenuDetail("얼큰순대국",7000, R.drawable.test2, "숙취 해소에는 이만한게 없지용~!!", "한식", 0));
        menuDetailList.add(new MenuDetail("이름이 엄청나게 긴 메뉴입니당", 12000, R.drawable.test3, "메뉴 설명 짧게도 해보고", "한식", 0));

        if(adapterModel != null) {
            adapterModel.setItemList(menuDetailList);
        }
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
