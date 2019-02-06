package com.aone.menurandomchoice.views.ownersignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerSignUpBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.ownerdetail.OwnerDetailActivity;
import com.aone.menurandomchoice.views.ownerlogin.OwnerLoginActivity;

public class OwnerSignUpActivity
        extends BaseActivity<ActivityOwnerSignUpBinding, OwnerSignUpContract.View, OwnerSignUpContract.Presenter>
        implements OwnerSignUpContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_owner_sign_up;
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

    public void onSignUpRequestClick(View view) {
        long userId = getIntent().getLongExtra(OwnerLoginActivity.EXTRA_USER_ID, -1);
        String accessKey = getDataBinding().activityOwnerSignUpEtAccessKey.getText().toString();
        getPresenter().requestSignUp(userId, accessKey);
    }

    @Override
    public void moveToOwnerDetailActivity(long userId) {
        Intent ownerDetailIntent = new Intent(OwnerSignUpActivity.this, OwnerDetailActivity.class);
        startActivity(ownerDetailIntent);
        finish();
    }

}
