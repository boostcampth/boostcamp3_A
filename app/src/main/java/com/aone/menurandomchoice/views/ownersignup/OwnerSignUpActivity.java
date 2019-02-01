package com.aone.menurandomchoice.views.ownersignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerSignUpBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;

public class OwnerSignUpActivity
        extends BaseActivity<ActivityOwnerSignUpBinding, OwnerSignUpContract.View, OwnerSignUpContract.Presenter>
        implements OwnerSignUpContract.View {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpBackArrow();
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

    private void setUpBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

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
