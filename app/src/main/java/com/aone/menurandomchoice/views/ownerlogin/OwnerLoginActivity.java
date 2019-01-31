package com.aone.menurandomchoice.views.ownerlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerLoginBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;

public class OwnerLoginActivity
        extends BaseActivity<ActivityOwnerLoginBinding, OwnerLoginContract.View, OwnerLoginContract.Presenter>
        implements OwnerLoginContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpBackArrow();
        requestLoginCheckToPresenter();
    }

    private void setUpBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @NonNull
    @Override
    protected ActivityOwnerLoginBinding setUpDataBinding() {
        ActivityOwnerLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_login);
        binding.setActivity(this);

        return binding;
    }

    @NonNull
    @Override
    protected OwnerLoginContract.Presenter setUpPresenter() {
        return new OwnerLoginPresenter();
    }

    @NonNull
    @Override
    protected OwnerLoginContract.View getView() {
        return this;
    }

    @Override
    protected void onSaveInstanceStateToBundle(@NonNull Bundle outState) {

    }

    private void requestLoginCheckToPresenter() {
        presenter.requestLoginCheck();
    }

    public void kakaoTalkAccountLoginClick(View view) {
        presenter.requestKaKaoAccountLogin();
    }

    public void kakaoTalkOtherAccountLoginClick(View view) {
        presenter.requestOtherKaKaoAccountLogin();
    }

}
