package com.aone.menurandomchoice.views.main;

import com.aone.menurandomchoice.views.base.BasePresenter;

public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter  {

    @Override
    public void handlingCustomerEnterButtonClick() {
        if(isAttachView()) {
            getView().moveToCustomerMainActivity();
        }
    }

    @Override
    public void handlingOwnerLoginButtonClick() {

    }

}
