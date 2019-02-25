package com.aone.menurandomchoice.views.menuselect;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityMenuSelectBinding;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.menuselect.adapter.MenuSelectOverlapViewAdapter;
import com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity;

public class MenuSelectActivity
        extends BaseActivity<ActivityMenuSelectBinding, MenuSelectContract.View, MenuSelectContract.Presenter>
        implements MenuSelectContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityToDataBinding();
        setUpOverlapView();
        passedGetIntentInfoToPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu_select;
    }

    @NonNull
    @Override
    protected MenuSelectContract.Presenter setUpPresenter() {
        return new MenuSelectPresenter();
    }

    @NonNull
    @Override
    protected MenuSelectContract.View getView() {
        return this;
    }

    private void setUpActivityToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
    }

    private void setUpOverlapView() {
        MenuSelectOverlapViewAdapter menuSelectOverlapViewAdapter = new MenuSelectOverlapViewAdapter();
        getDataBinding().activityMenuSelectOverlapView.setOverlapLoopViewAdapter(menuSelectOverlapViewAdapter);
        getPresenter().setAdapterModel(menuSelectOverlapViewAdapter);
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
    public void moveToOwnerStoreActivity(UserAccessInfo userAccessInfo) {
        Intent ownerStoreIntent = new Intent(this, OwnerStoreActivity.class);
        ownerStoreIntent.putExtra(OwnerStoreActivity.EXTRA_USER_ACCESS_INFO, userAccessInfo);
        startActivity(ownerStoreIntent);
    }

    private void passedGetIntentInfoToPresenter() {
        MenuSearchRequest menuSearchRequest = getIntent().getParcelableExtra(getString(R.string.activity_customer_main_extra_menu_data));
        getPresenter().requestMenuList(menuSearchRequest);
    }

}
