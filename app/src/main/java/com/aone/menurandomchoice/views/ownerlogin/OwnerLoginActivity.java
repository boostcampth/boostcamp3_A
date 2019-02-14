package com.aone.menurandomchoice.views.ownerlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerLoginBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.ownerdetail.OwnerDetailActivity;
import com.aone.menurandomchoice.views.ownersignup.OwnerSignUpActivity;
import com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity;

public class OwnerLoginActivity
        extends BaseActivity<ActivityOwnerLoginBinding, OwnerLoginContract.View, OwnerLoginContract.Presenter>
        implements OwnerLoginContract.View {

    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
        requestLoginCheckToPresenter();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!getPresenter().isNeedKakaoSDKLoginScreen(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_owner_login;
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
    public void moveToOwnerDetailActivity(long userId) {
        Intent ownerDetailIntent = new Intent(this, OwnerStoreActivity.class);
        startActivity(ownerDetailIntent);
        finish();
    }

    @Override
    public void moveToSignUpActivity(long userId) {
        Intent signUpActivityIntent = new Intent(OwnerLoginActivity.this, OwnerSignUpActivity.class);
        signUpActivityIntent.putExtra(EXTRA_USER_ID, userId);
        startActivity(signUpActivityIntent);
        finish();
    }

    private void requestLoginCheckToPresenter() {
        getPresenter().handlingLoggedInAccount();
    }

}
