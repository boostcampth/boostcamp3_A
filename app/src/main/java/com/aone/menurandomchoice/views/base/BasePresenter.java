package com.aone.menurandomchoice.views.base;

import com.aone.menurandomchoice.repository.DataRepository;
import com.aone.menurandomchoice.repository.Repository;

import androidx.annotation.NonNull;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private V view;
    private Repository repository;

    protected BasePresenter() {
        repository = DataRepository.getInstance();
    }

    @Override
    public void attachView(@NonNull V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
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

}
