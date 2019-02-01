package com.aone.menurandomchoice.views.ownersignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerSignUpBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;

public class OwnerSignUpActivity
        extends BaseActivity<ActivityOwnerSignUpBinding, OwnerSignUpContract.View, OwnerSignUpContract.Presenter>
        implements OwnerSignUpContract.View {

    @NonNull
    @Override
    protected ActivityOwnerSignUpBinding setUpDataBinding() {
        ActivityOwnerSignUpBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_owner_sign_up);
        dataBinding.setActivity(this);

        return dataBinding;
    }

    @NonNull
    @Override
    protected OwnerSignUpContract.Presenter setUpPresenter() {
        return new OwnerSignUpPresenter();
    }

    @NonNull
    @Override
    protected OwnerSignUpContract.View getView() {
        return this;
    }

    @Override
    protected void onSaveInstanceStateToBundle(@NonNull Bundle outState) {

    }

}
