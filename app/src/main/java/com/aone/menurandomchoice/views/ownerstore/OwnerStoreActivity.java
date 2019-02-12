package com.aone.menurandomchoice.views.ownerstore;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerStoreBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BaseActivity;

import androidx.annotation.NonNull;

public class OwnerStoreActivity
        extends BaseActivity<ActivityOwnerStoreBinding, OwnerStoreContract.View, OwnerStoreContract.Presenter>
        implements OwnerStoreContract.View {

    public static final String EXTRA_MENU = "EXTRA_MENU";
    public static final String EXTRA_STORE = "EXTRA_STORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityToDataBinding();
    }

    @Override
    protected void onStart() {
        super.onStart();

        int storeIdx = getIntent().getIntExtra("EXTRA_STORE_IDX", 0);
        getPresenter().loadStoreDetail(storeIdx);
    }

    @Override
    protected void onStop() {
        super.onStop();

        getPresenter().stopNetwork();
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
                ///moveToOwnerEditPage();
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

    @Override
    public void moveToOwnerEditPage(StoreDetail storeDetail) {
        /*
        Intent ownerEditIntent = new Intent(OwnerStoreActivity.this, ownerEditActivity.class);
        ownerEditIntent.putExtra(EXTRA_STORE, storeDetail);
        startActivity(ownerEditIntent);
        */
    }

    @Override
    public void moveToMenuDetailPage(MenuDetail menuDetail) {
        // Todo. menuDetailActivity 연결

        /*
        Intent menuDetailIntent = new Intent(OwnerStoreActivity.this, menuDetailActivity.class);
        menuDetailIntent.putExtra(EXTRA_MENU, menuDetail);
        startActivity(menuDetailIntent);
        */
    }

    public void onMapClick(View view) {
        //Todo. map 페이지로 이동
    }

    @Override
    public void showStoreDetail(StoreDetail storeDetail) {
        getDataBinding().setStoreDetail(storeDetail);
    }
}
