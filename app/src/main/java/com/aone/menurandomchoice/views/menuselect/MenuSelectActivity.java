package com.aone.menurandomchoice.views.menuselect;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

    public static final String EXTRA_MENU_SEARCH_REQUEST = "EXTRA_MENU_SEARCH_REQUEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
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

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
    }

    private void setUpOverlapView() {
        MenuSelectOverlapViewAdapter menuSelectOverlapViewAdapter = new MenuSelectOverlapViewAdapter();
        getDataBinding().activityMenuSelectOverlapView.setOverlapViewAdapter(menuSelectOverlapViewAdapter);
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
        MenuSearchRequest menuSearchRequest = new MenuSearchRequest();

        //menuSearchRequest는 getIntent를 통해 얻게된 객체를 넘겨줘야 하지만
        //아직 전 화면의 기능이 완성되지 않았기 때문에 테스트를 위해 빈 객체를
        //넘겨줌
        //MenuSearchRequest menuSearchRequest = getIntent().getParcelableExtra(EXTRA_MENU_SEARCH_REQUEST);

        getPresenter().requestMenuList(menuSearchRequest);
    }

}
