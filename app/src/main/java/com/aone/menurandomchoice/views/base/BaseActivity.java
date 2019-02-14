package com.aone.menurandomchoice.views.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends AppCompatActivity
        implements BaseContract.View {

    private B dataBinding;
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp();
        attachViewToPresenter();
    }

    @Override
    protected void onStart() {
        attachViewToPresenter();

        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        detachViewFromPresenter();

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        detachViewFromPresenter();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        detachViewFromPresenter();

        super.onDestroy();
    }

    private void setUp() {
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        presenter = setUpPresenter();

        setUpBackArrow();
    }

    private void setUpBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @NonNull
    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public void showToastMessage(@NonNull String message) {
        Toast.makeText(getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected B getDataBinding() {
        return dataBinding;
    }

    protected P getPresenter() {
        return presenter;
    }

    protected void attachViewToPresenter() {
        attachToPresenter();
    }

    protected void detachViewFromPresenter() {
        detachFromPresenter();
    }

    private void attachToPresenter() {
        if(presenter != null && !presenter.isAttachView()) {
            presenter.attachView(getView());
        }
    }

    private void detachFromPresenter() {
        if(presenter != null && presenter.isAttachView()) {
            presenter.detachView();
        }
    }

    abstract protected int getLayoutId();

    @NonNull
    abstract protected P setUpPresenter();

    @NonNull
    abstract protected V getView();

}
