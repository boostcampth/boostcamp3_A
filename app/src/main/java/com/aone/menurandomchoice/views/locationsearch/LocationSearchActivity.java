package com.aone.menurandomchoice.views.locationsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityLocationSearchBinding;
import com.aone.menurandomchoice.utils.KeyboardUtil;
import com.aone.menurandomchoice.views.base.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LocationSearchActivity extends BaseActivity<ActivityLocationSearchBinding, LocationSearchContract.View, LocationSearchContract.Presenter>
                                    implements LocationSearchContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityToDataBinding();
        setUpSearchToolBar();
        initView();
    }

    private void setUpActivityToDataBinding() { getDataBinding().setActivity(this); }

    private void initView() {
        LocationSearchAdapter adapter = new LocationSearchAdapter();
        RecyclerView recyclerView = getDataBinding().rcContent;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        getPresenter().setAdapter(adapter);
    }

    private void setUpSearchToolBar() {
        Toolbar toolbar = getDataBinding().searchBox.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getDataBinding().searchBox.etSearch.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId == EditorInfo.IME_ACTION_SEARCH){
                            requestLocationSearchWord();
                            return true;
                        }
                        return false;
                    }
                });
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected int getLayoutId() { return R.layout.activity_location_search; }

    @NonNull
    @Override
    protected LocationSearchContract.Presenter setUpPresenter() { return new LocationSearchPresenter(); }

    @NonNull
    @Override
    protected  LocationSearchContract.View getView() { return this; }

    public void requestLocationSearchWord() {
        KeyboardUtil.hideKeyboard(this);
        String inputAddress = getDataBinding().searchBox.etSearch.getText().toString();
        String REST_API_KEY = getAppContext().getString(R.string.KAKAO_REST_API_KEY);
        getPresenter().requestLocationSearch(inputAddress, REST_API_KEY);
    }
}
