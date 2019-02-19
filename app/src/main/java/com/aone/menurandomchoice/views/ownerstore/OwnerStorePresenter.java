package com.aone.menurandomchoice.views.ownerstore;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLogoutListener;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;

public class OwnerStorePresenter extends BasePresenter<OwnerStoreContract.View> implements  OwnerStoreContract.Presenter {

    @Override
    public void loadStoreDetail(int storeIdx) {
        getRepository().loadStoreDetail(storeIdx, new NetworkResponseListener<StoreDetail>() {
            @Override
            public void onReceived(@NonNull StoreDetail storeDetail) {
                if (isAttachView()) {
                    getView().showStoreDetail(storeDetail);
                }
            }

            @Override
            public void onError(JMTErrorCode errorCode) {
                handlingJMTError(errorCode);
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


    @Override
    public void onLogoutClick() {
        getRepository().executeKakaoAccountLogout(onKakaoLogoutListener);
    }

    private void handlingJMTError(JMTErrorCode errorCode) {
        if(isAttachView()) {
            String errorMessage = GlobalApplication
                    .getGlobalApplicationContext()
                    .getString(errorCode.getStringResourceId());

            getView().showToastMessage(errorMessage);
        }
    }

    private OnKakaoLogoutListener onKakaoLogoutListener = new OnKakaoLogoutListener() {
        @Override
        public void onKakaoLogoutSuccess() {
            if(isAttachView()) {
                String logoutMessage = GlobalApplication
                        .getGlobalApplicationContext()
                        .getString(R.string.activity_owner_store_logout);
                getView().showToastMessage(logoutMessage);
                getView().finishOwnerStorePage();
            }
        }
    };

}
