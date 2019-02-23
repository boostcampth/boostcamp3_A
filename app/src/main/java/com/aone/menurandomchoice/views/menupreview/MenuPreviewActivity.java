package com.aone.menurandomchoice.views.menupreview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityMenuPreviewBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.utils.GlideUtil;

public class MenuPreviewActivity extends AppCompatActivity {

    public static final String EXTRA_MENU_DETAIL_ITEM = "EXTRA_MENU_DETAIL_ITEM";

    private ActivityMenuPreviewBinding activityMenuPreviewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpDataBinding();
        processPassedIntent();
        setUpBackArrow();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setPreviewImage();

    }

    private void setUpBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpDataBinding() {
        activityMenuPreviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu_preview);
    }

    private void processPassedIntent() {
        Intent intent = getIntent();
        MenuDetail menuDetail = intent.getParcelableExtra(EXTRA_MENU_DETAIL_ITEM);
        activityMenuPreviewBinding.setMenuDetail(menuDetail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setPreviewImage() {
        GlideUtil.loadImageWithSkipCache(activityMenuPreviewBinding.previewItemLayout.itemMenuSelectIv,
                activityMenuPreviewBinding.getMenuDetail().getPhotoUrl(),
                activityMenuPreviewBinding.previewItemLayout.itemMenuSelectProgressBar);
    }
    
}
