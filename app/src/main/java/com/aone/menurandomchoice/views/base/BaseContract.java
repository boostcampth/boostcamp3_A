package com.aone.menurandomchoice.views.base;

import android.content.Context;

import androidx.annotation.NonNull;


public interface BaseContract {

    interface View {

        Context getAppContext();

        void showToastMessage(@NonNull String message);

    }

    interface Presenter<V extends BaseContract.View> {

        void attachView(@NonNull V view);

        void detachView();

        boolean isAttachView();

    }

}
