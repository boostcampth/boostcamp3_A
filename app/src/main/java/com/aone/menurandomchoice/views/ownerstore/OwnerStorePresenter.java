package com.aone.menurandomchoice.views.ownerstore;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLogoutListener;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.utils.ClickUtil;
import com.aone.menurandomchoice.utils.StringUtil;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class OwnerStorePresenter extends BasePresenter<OwnerStoreContract.View> implements  OwnerStoreContract.Presenter {

    @Override
    public void loadStoreDetail(int storeIdx, boolean isOwner) {
        if(isOwner) {
            loadStoreDetailToOwner(storeIdx);
        } else {
            loadStoreDetailToCustomer(storeIdx);
        }
    }

    @Override
    public void onMenuDetailClick(View view, MenuDetail menuDetail) {
        ClickUtil.preventDuplicateClick(view);

        if(TextUtils.isEmpty(menuDetail.getPhotoUrl())) {
            sendMessageToView(R.string.activity_owner_detail_not_menu_page);
        } else {
            getView().moveToMenuPreviewPage(menuDetail);
        }
    }

    @Override
    public void onMapClick(double latitude, double longitude) {
        if(isAttachView()) {
            getView().moveToMapDetailPage(latitude, longitude);
        }
    }

    @Override
    public void stopNetwork() {
        getRepository().cancelAll();
    }


    @Override
    public void onLogoutClick(View view) {
        ClickUtil.preventDuplicateClick(view);
        showLogoutCheckDialog();
    }

    private OnKakaoLogoutListener onKakaoLogoutListener = new OnKakaoLogoutListener() {
        @Override
        public void onKakaoLogoutSuccess() {
            sendMessageToView(R.string.activity_owner_store_logout);
            if(isAttachView()) {
                getView().finishOwnerStorePage();
            }
        }
    };

    private void showLogoutCheckDialog(){
        if(isAttachView()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getView().getActivityContext());
            builder.setMessage(StringUtil.getString(R.string.activity_logout_guide));

            builder.setPositiveButton(StringUtil.getString(R.string.activity_logout_yes),
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getRepository().executeKakaoAccountLogout(onKakaoLogoutListener);
                }
            });

            builder.setNegativeButton(StringUtil.getString(R.string.activity_logout_no),
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendMessageToView(R.string.activity_logout_no_guide);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void loadStoreDetailToOwner(int storeIdx) {
        showProgressBarOfView();

        getRepository().loadStoreDetail(storeIdx, new NetworkResponseListener<StoreDetail>() {
            @Override
            public void onReceived(@NonNull StoreDetail storeDetail) {
                hideProgressBarOfView();

                if (isAttachView()) {
                    getView().showStoreDetail(storeDetail);
                }
            }

            @Override
            public void onError(JMTErrorCode errorCode) {
                hideProgressBarOfView();

                sendMessageToView(errorCode.getStringResourceId());
            }
        });
    }

    public void loadStoreDetailToCustomer(int storeIdx) {
        showProgressBarOfView();

        getRepository().requestStoreDetail(storeIdx, new NetworkResponseListener<StoreDetail>() {
            @Override
            public void onReceived(@NonNull StoreDetail response) {
                hideProgressBarOfView();

                if(isAttachView()) {
                    getView().showStoreDetail(response);
                }
            }

            @Override
            public void onError(JMTErrorCode errorCode) {
                hideProgressBarOfView();

                sendMessageToView(errorCode.getStringResourceId());
            }
        });
    }
}
