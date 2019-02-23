package com.aone.menurandomchoice.views.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.base.widget.CustomProgressDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends AppCompatActivity
        implements BaseContract.View {

    private B dataBinding;
    private P presenter;
    private CustomProgressDialog customProgressDialog;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        presenter = setUpPresenter();

        setUpBackArrow();
        setUpProgressDialog();
    }

    private void setUpBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpProgressDialog() {
        customProgressDialog = new CustomProgressDialog();
    }

    @NonNull
    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @NonNull
    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void showToastMessage(@NonNull final String message) {
        Toast.makeText(getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        customProgressDialog.show(this);
    }

    @Override
    public void hideProgressDialog() {
        customProgressDialog.hide();
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
