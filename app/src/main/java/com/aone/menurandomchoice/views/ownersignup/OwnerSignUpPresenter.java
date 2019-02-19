package com.aone.menurandomchoice.views.ownersignup;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;

public class OwnerSignUpPresenter extends BasePresenter<OwnerSignUpContract.View>
        implements OwnerSignUpContract.Presenter {

    private static final int POSSIBLE_ACCESS_KEY_LENGTH = 6;

    @Override
    public void requestSignUp(final long userId, @NonNull String accessKey) {
        if(isAttachView()) {
            getView().showProgressDialog();
            if (isPossibleAccessKey(accessKey)) {
                requestSignUpToRepository(userId, accessKey);
            } else {
                getView().hideProgressDialog();
                sendMessageToView(R.string.activity_owner_access_key_guide);
            }
        }

    }

    private boolean isPossibleAccessKey(String accessKey) {
        return accessKey.length() == POSSIBLE_ACCESS_KEY_LENGTH ;
    }

    private void requestSignUpToRepository(long userId, @NonNull String accessKey) {
        getRepository().requestSignUp(userId, accessKey, new NetworkResponseListener<LoginData>() {
            @Override
            public void onReceived(@NonNull LoginData loginData) {
                getRepository().addDefaultStoreDetail();
                moveToOwnerDetailActivity(loginData);
            }

            @Override
            public void onError(JMTErrorCode errorCode) {
                getView().hideProgressDialog();
                sendMessageToView(errorCode.getStringResourceId());
            }
        });
    }

    private void moveToOwnerDetailActivity(LoginData loginData) {
        if (isAttachView()) {
            getView().hideProgressDialog();
            getView().moveToOwnerStoreActivity(new UserAccessInfo(loginData.getStoreIdx(), true));
        }
    }

    private void sendMessageToView(int messageResourceId) {
        if(isAttachView()) {
            String message = GlobalApplication
                    .getGlobalApplicationContext()
                    .getString(messageResourceId);

            getView().showToastMessage(message);
        }
    }

}
