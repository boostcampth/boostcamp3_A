package com.aone.menurandomchoice.views.ownerlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerLoginBinding;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseActivity;
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
        if(getPresenter().isNeedKakaoSDKLoginScreen(requestCode, resultCode, data)) {
            attachViewToPresenter();
        } else {
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
    public void moveToOwnerStoreActivity(@NonNull UserAccessInfo userAccessInfo) {
        Intent ownerDetailIntent = new Intent(this, OwnerStoreActivity.class);
        ownerDetailIntent.putExtra(OwnerStoreActivity.EXTRA_USER_ACCESS_INFO, userAccessInfo);
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

}
