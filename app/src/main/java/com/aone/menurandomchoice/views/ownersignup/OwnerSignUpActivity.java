package com.aone.menurandomchoice.views.ownersignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerSignUpBinding;
import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.ownerlogin.OwnerLoginActivity;
import com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity;

public class OwnerSignUpActivity
        extends BaseActivity<ActivityOwnerSignUpBinding, OwnerSignUpContract.View, OwnerSignUpContract.Presenter>
        implements OwnerSignUpContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityToDataBinding();
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

    private void setUpActivityToDataBinding() {
        getDataBinding().setActivity(this);
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
    public void moveToOwnerStoreActivity(@NonNull UserAccessInfo userAccessInfo) {
        Intent ownerStoreIntent = new Intent(OwnerSignUpActivity.this, OwnerStoreActivity.class);
        ownerStoreIntent.putExtra(OwnerStoreActivity.EXTRA_USER_ACCESS_INFO, userAccessInfo);
        startActivity(ownerStoreIntent);
        finish();
    }

}
