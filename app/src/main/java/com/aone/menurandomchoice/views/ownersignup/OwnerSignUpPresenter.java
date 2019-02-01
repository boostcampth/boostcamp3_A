package com.aone.menurandomchoice.views.ownersignup;

import com.aone.menurandomchoice.repository.server.OnSignUpRequestListener;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;

public class OwnerSignUpPresenter extends BasePresenter<OwnerSignUpContract.View>
        implements OwnerSignUpContract.Presenter {

    private static final int POSSIBLE_ACCESS_KEY_LENGTH = 6;

    /**
     * When the server implementation is complete,
     * we must implement error handling logic
     */
    @Override
    public void requestSignUp(final long userId, @NonNull String accessKey) {
        if(accessKey.length() == POSSIBLE_ACCESS_KEY_LENGTH) {
            getRepository().requestSignUp(userId, accessKey, new OnSignUpRequestListener() {
                @Override
                public void onSignUpSuccess() {
                    getView().moveToOwnerDetailActivity(userId);
                }

                @Override
                public void onSignUpFail() {
                    getView().showToastMessage("가입에 실패했습니다.");
                }
            });
        } else {
            getView().showToastMessage("인증번호를 올바르게 입력해주세용");
        }
    }

}
