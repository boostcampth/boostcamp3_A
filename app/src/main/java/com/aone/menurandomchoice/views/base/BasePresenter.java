package com.aone.menurandomchoice.views.base;

import android.os.Handler;
import android.view.View;

import com.aone.menurandomchoice.repository.DataRepository;
import com.aone.menurandomchoice.repository.Repository;

import androidx.annotation.NonNull;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private static final int PREVENT_DUPLICATE_DELAY = 200;

    private V view;
    private Repository repository;
    protected Handler handler;

    protected BasePresenter() {
        setUp();
    }

    private void setUp() {
        repository = DataRepository.getInstance();
        handler = new Handler();
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

    protected void preventDuplicateClick(final View view) {
        view.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, PREVENT_DUPLICATE_DELAY);
    }

}
