package com.aone.menurandomchoice.views.ownerstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerStoreBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.menupreview.MenuPreviewActivity;
import com.aone.menurandomchoice.views.storeedit.StoreEditActivity;

import androidx.annotation.NonNull;

public class OwnerStoreActivity
        extends BaseActivity<ActivityOwnerStoreBinding, OwnerStoreContract.View, OwnerStoreContract.Presenter>
        implements OwnerStoreContract.View {

    public static final String EXTRA_MENU = "EXTRA_MENU";
    public static final String EXTRA_STORE = "EXTRA_STORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();

        initMapView();
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
                moveToOwnerEditPage(getDataBinding().getStoreDetail());
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

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
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

        Intent storeEditIntent = new Intent(OwnerStoreActivity.this, StoreEditActivity.class);
        storeEditIntent.putExtra(EXTRA_STORE, storeDetail);
        startActivity(storeEditIntent);
    }

    @Override
    public void moveToMenuPreviewPage(MenuDetail menuDetail) {
        Intent menuPreviewIntent = new Intent(this, MenuPreviewActivity.class);
        menuPreviewIntent.putExtra(MenuPreviewActivity.EXTRA_MENU_DETAIL_ITEM, menuDetail);
        startActivity(menuPreviewIntent);
    }

    @Override
    public void showStoreDetail(StoreDetail storeDetail) {

        getDataBinding().setStoreDetail(storeDetail);

        getDataBinding().activityOwnerStoreMenu1.setMenuDetail(storeDetail.getMenuList().get(0));
        getDataBinding().activityOwnerStoreMenu2.setMenuDetail(storeDetail.getMenuList().get(1));
        getDataBinding().activityOwnerStoreMenu3.setMenuDetail(storeDetail.getMenuList().get(2));

        //Todo. setMapview(storeDetail.getLatitude(), storeDetail.getLongitude());
    }

    public void initMapView() {

    }

    public void setMapView(double latitude, double longitude) {
    }
}
