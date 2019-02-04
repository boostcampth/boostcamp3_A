package com.aone.menurandomchoice.views.ownerstore;
import android.os.Bundle;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerStoreBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

public class OwnerStoreActivity
        extends BaseActivity<ActivityOwnerStoreBinding, OwnerStoreContract.View, OwnerStoreContract.Presenter>
        implements OwnerStoreContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    protected ActivityOwnerStoreBinding setUpDataBinding() {
        ActivityOwnerStoreBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_store);
        binding.setActivity(this);
        return binding;
    }

    @NonNull
    @Override
    protected OwnerStoreContract.Presenter setUpPresenter() {
        return new OwnerStorePresenter();
    }

    @NonNull
    @Override
    protected OwnerStoreContract.View getView() {
        return this;
    }


    @Override
    protected void onSaveInstanceStateToBundle(@NonNull Bundle outState) {
    }


}
