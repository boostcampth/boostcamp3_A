package com.aone.menurandomchoice.views.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.os.Bundle;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.customermain.CustomerMainActivity;
import com.aone.menurandomchoice.databinding.ActivityMainBinding;
import com.aone.menurandomchoice.utils.GlideUtil;
import com.aone.menurandomchoice.views.ownerlogin.OwnerLoginActivity;
import com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity;
import com.aone.menurandomchoice.views.storeedit.StoreEditActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity
        extends BaseActivity<ActivityMainBinding, MainContract.View, MainContract.Presenter>
        implements MainContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected MainContract.Presenter setUpPresenter() {
        return new MainPresenter();
    }

    @NonNull
    @Override
    protected MainContract.View getView() {
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadScreenIcon();
    }

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
    }

    @Override
    public void moveToCustomerMainActivity() {
        Intent customerMainIntent = new Intent(MainActivity.this, CustomerMainActivity.class);
        startActivity(customerMainIntent);
    }

    @Override
    public void moveToOwnerLoginActivity() {
        Intent ownerLoginIntent = new Intent(this, OwnerLoginActivity.class);
        startActivity(ownerLoginIntent);
    }

    @Override
    public void moveToOwnerStoreActivity(UserAccessInfo userAccessInfo) {
        Intent ownerStoreIntent = new Intent(this, OwnerStoreActivity.class);
        ownerStoreIntent.putExtra(OwnerStoreActivity.EXTRA_USER_ACCESS_INFO, userAccessInfo);
        startActivity(ownerStoreIntent);
    }

    private void loadScreenIcon() {
        GlideUtil.loadImage(getDataBinding().activityMainCustomerTobaccoIv, R.drawable.customer_tobacco_icon);
        GlideUtil.loadImage(getDataBinding().activityMainCustomerHamburgerIv, R.drawable.customer_hamburger_icon);
        GlideUtil.loadImage(getDataBinding().activityMainCustomerPopcornIv, R.drawable.customer_popcorn_icon);
        GlideUtil.loadImage(getDataBinding().activityMainCustomerSalmonIv, R.drawable.customer_salmon_icon);
        GlideUtil.loadImage(getDataBinding().activityMainCustomerDoughnutIv, R.drawable.customer_doughnut_icon);
        GlideUtil.loadImage(getDataBinding().activityMainCustomerSandwichIv, R.drawable.customer_sandwich_icon);
        GlideUtil.loadImage(getDataBinding().activityMainOwnerIv, R.drawable.owner_icon);
    }

}
