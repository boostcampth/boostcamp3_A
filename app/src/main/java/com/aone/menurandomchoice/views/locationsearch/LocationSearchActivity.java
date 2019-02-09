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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LocationSearchActivity extends BaseActivity<ActivityLocationSearchBinding, LocationSearchContract.View, LocationSearchContract.Presenter>
                                    implements LocationSearchContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = getDataBinding().searchBox.toolbar;
        setSupportActionBar(toolbar);

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

        setUpBackArrow();
        initView();
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

    private void setUpBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @NonNull
    @Override
    protected  ActivityLocationSearchBinding setUpDataBinding() {
        ActivityLocationSearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_location_search);
        binding.setActivity(this);
        return binding;
    }

    @NonNull
    @Override
    protected LocationSearchContract.Presenter setUpPresenter() { return new LocationSearchPresenter(); }

    @NonNull
    @Override
    protected  LocationSearchContract.View getView() { return this; }

    @Override
    protected void onSaveInstanceStateToBundle(@NonNull Bundle outState) {
    }

    private void initView() {
        LocationSearchAdapter adapter = new LocationSearchAdapter();
        RecyclerView recyclerView = getDataBinding().rcContent;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        getPresenter().setAdapter(adapter);
    }

    public void requestLocationSearchWord() {
        KeyboardUtil.hideKeyboard(this);
        String inputAddress = getDataBinding().searchBox.etSearch.getText().toString();
        getPresenter().requestLocationSearch(inputAddress);
    }
}
