package com.aone.menurandomchoice.views.base;

import com.aone.menurandomchoice.repository.DataRepository;
import com.aone.menurandomchoice.repository.Repository;
import com.aone.menurandomchoice.utils.StringUtil;

import androidx.annotation.NonNull;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private V view;
    private Repository repository;

    protected BasePresenter() {
        setUp();
    }

    private void setUp() {
        repository = DataRepository.getInstance();
    }

    @Override
    public void attachView(@NonNull V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view.hideProgressDialog();
        view = null;
    }

    @Override
    public boolean isAttachView() {
        return view != null;
    }

    protected V getView() {
        return view;
    }

    protected Repository getRepository() {
        return repository;
    }

    protected void showProgressBarOfView() {
        if(isAttachView()) {
            getView().showProgressDialog();
        }
    }

    protected void hideProgressBarOfView() {
        if(isAttachView()) {
            getView().hideProgressDialog();
        }
    }

    protected void sendMessageToView(int resourceId) {
        if(isAttachView()) {
            String errorMessage = StringUtil.getString(resourceId);
            getView().showToastMessage(errorMessage);
        }
    }

}
