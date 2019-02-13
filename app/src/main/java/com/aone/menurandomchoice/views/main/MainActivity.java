package com.aone.menurandomchoice.views.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.customermain.CustomerMainActivity;
import com.aone.menurandomchoice.views.locationsearch.LocationSearchActivity;
import com.aone.menurandomchoice.views.locationsearch.LocationSearchPresenter;
import com.aone.menurandomchoice.databinding.ActivityMainBinding;
import com.aone.menurandomchoice.utils.GlideUtil;
import com.aone.menurandomchoice.views.menuselect.MenuSelectActivity;
import com.aone.menurandomchoice.views.ownerlogin.OwnerLoginActivity;
import com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDataBinding();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadScreenIcon();
    }

    private void setUpDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setActivity(this);
    }

    private void loadScreenIcon() {
        GlideUtil.loadImage(activityMainBinding.activityMainCustomerTobaccoIv, R.drawable.customer_tobacco_icon);
        GlideUtil.loadImage(activityMainBinding.activityMainCustomerHamburgerIv, R.drawable.customer_hamburger_icon);
        GlideUtil.loadImage(activityMainBinding.activityMainCustomerPopcornIv, R.drawable.customer_popcorn_icon);
        GlideUtil.loadImage(activityMainBinding.activityMainCustomerSalmonIv, R.drawable.customer_salmon_icon);
        GlideUtil.loadImage(activityMainBinding.activityMainCustomerDoughnutIv, R.drawable.customer_doughnut_icon);
        GlideUtil.loadImage(activityMainBinding.activityMainCustomerSandwichIv, R.drawable.customer_sandwich_icon);
        GlideUtil.loadImage(activityMainBinding.activityMainOwnerIv, R.drawable.owner_icon);
    }

    public void moveToMenuSelectActivity(View view) {
        Intent menuSelectIntent = new Intent(MainActivity.this, CustomerMainActivity.class);
        startActivity(menuSelectIntent);
    }

    public void moveToOwnerLoginActivity(View view) {
        Intent ownerLoginIntent = new Intent(MainActivity.this, LocationSearchActivity.class);
        startActivity(ownerLoginIntent);
    }

}
