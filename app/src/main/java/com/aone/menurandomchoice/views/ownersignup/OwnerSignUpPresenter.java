package com.aone.menurandomchoice.views.ownersignup;

import com.aone.menurandomchoice.repository.remote.OnSignUpRequestListener;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;

public class OwnerSignUpPresenter extends BasePresenter<OwnerSignUpContract.View>
        implements OwnerSignUpContract.Presenter {

    private static final int POSSIBLE_ACCESS_KEY_LENGTH = 6;

    @Override
    public void requestSignUp(final long userId, @NonNull String accessKey) {
        if (isPossibleAccessKey(accessKey)) {
            requestSignUpToRepository(userId, accessKey);
        } else {
            sendMessageToView("인증번호를 올바르게 입력해주세요");
        }
    }

    private boolean isPossibleAccessKey(String accessKey) {
        return accessKey.length() == POSSIBLE_ACCESS_KEY_LENGTH ;
    }

    private void requestSignUpToRepository(final long userId, @NonNull String accessKey) {
        getRepository().requestSignUp(userId, accessKey, new OnSignUpRequestListener() {
            @Override
            public void onSignUpSuccess() {

                getRepository().addStoreDetail();

                moveToOwnerDetailActivity(userId);
            }

            /**
             * When the server implementation is complete,
             * we must implement error handling logic
             */
            @Override
            public void onSignUpFail() {
                sendMessageToView("가입에 실패했습니다.");
            }
        });
    }

    private void moveToOwnerDetailActivity(long userId) {
        if (isAttachView()) {
            getView().moveToOwnerStoreActivity(userId);
        }
    }

    private void sendMessageToView(String message) {
        if(isAttachView()) {
            getView().showToastMessage(message);
        }
    }

}
