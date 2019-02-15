package com.aone.menurandomchoice.views.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.customermain.CustomerMainActivity;
import com.aone.menurandomchoice.databinding.ActivityMainBinding;
import com.aone.menurandomchoice.utils.GlideUtil;
import com.aone.menurandomchoice.views.menuregister.MenuRegisterActivity;
import com.aone.menurandomchoice.views.menuselect.MenuSelectActivity;
import com.aone.menurandomchoice.views.ownerlogin.OwnerLoginActivity;
import com.aone.menurandomchoice.views.ownersignup.OwnerSignUpActivity;
import com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity;
import com.aone.menurandomchoice.views.storeedit.StoreEditActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpDataBinding();

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.aone.menurandomchoice", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
        /*
        Intent intent = new Intent(this, MenuRegisterActivity.class);
        startActivity(intent);
        */
        
        Intent intent = new Intent(this, OwnerStoreActivity.class);
        startActivity(intent);
    }

}
