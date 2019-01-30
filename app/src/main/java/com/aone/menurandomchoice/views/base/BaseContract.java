package com.aone.menurandomchoice.views.base;

import android.content.Context;


public interface BaseContract {

    interface View {

        Context getAppContext();

    }

    interface Presenter<V extends BaseContract.View> {

        void attachView(V view);

        void detachView();

        boolean isAttachView();

    }

}
