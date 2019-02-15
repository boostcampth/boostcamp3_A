package com.aone.menurandomchoice.views.ownersignup;

import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseContract;

import androidx.annotation.NonNull;

public interface OwnerSignUpContract {

    interface View extends BaseContract.View {

        void moveToOwnerStoreActivity(@NonNull UserAccessInfo userAccessInfo);

    }

    interface Presenter extends BaseContract.Presenter<OwnerSignUpContract.View> {

        void requestSignUp(long userId, @NonNull String accessKey);

    }

}
