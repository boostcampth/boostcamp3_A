package com.aone.menurandomchoice.views.ownerlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.data.DataRepository;
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

        dataBinding.activityOwnerTvPhonenumberGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRepository.getInstance().executeKakaoAccountLogout();
            }
        });
    }

    private void setUpBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
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
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onSaveInstanceStateToBundle(@NonNull Bundle outState) {
    }

    private void requestLoginCheckToPresenter() {
        presenter.handlingLoggedInAccount();
    }

    public void kakaoTalkAccountLoginClick(View view) {
        presenter.handlingDeviceKaKaoAccountLogin();
    }

    public void kakaoTalkOtherAccountLoginClick(View view) {
        presenter.handlingOtherKaKaoAccountLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!presenter.isNeedKakaoSDKLoginScreen(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
