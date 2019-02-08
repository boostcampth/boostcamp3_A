package com.aone.menurandomchoice.views.ownerstore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerStoreBinding;
import com.aone.menurandomchoice.repository.model.StoreDetail;
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

        setUpActivityToDataBinding();
    }

    @Override
    protected void onStart() {
        int storeIdx = getIntent().getIntExtra("EXTRA_STORE_IDX", 0);
        getPresenter().loadStoreDetail(storeIdx);

        super.onStart();
    }

    @Override
    protected void onStop() {
        getPresenter().stopNetwork();

        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.item_action_bar_edit:
                moveToOwnerEditPage();
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
        return R.layout.activity_owner_store;
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

    public void onMenuDetailClick(View view) {
        //Todo. menudeail 페이지로 이동
    }

    public void onMapClick(View view) {
        //Todo. map 페이지로 이동
    }

    @Override
    public void moveToOwnerEditPage() {
    }

    @Override
    public void showStoreDetail(StoreDetail storeDetail) {
        getDataBinding().setStoreDetail(storeDetail);
    }

}
