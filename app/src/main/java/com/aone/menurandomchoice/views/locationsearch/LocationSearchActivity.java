package com.aone.menurandomchoice.views.locationsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityLocationSearchBinding;
import com.aone.menurandomchoice.utils.KeyboardUtil;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.locationsearch.adapter.LocationSearchAdapterView;
import com.aone.menurandomchoice.views.locationsearch.adapter.OnViewHolderClickListener;
import com.aone.menurandomchoice.views.locationsearch.adapter.LocationSearchAdapter;
import com.aone.menurandomchoice.views.storelocation.StoreLocationActivity;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;

public class LocationSearchActivity
        extends BaseActivity<ActivityLocationSearchBinding, LocationSearchContract.View, LocationSearchContract.Presenter>
        implements LocationSearchContract.View {

    private LocationSearchAdapterView adapterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityToDataBinding();
        setUpSearchToolBar();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().stopNetwork();
    }

    private void setUpActivityToDataBinding() { getDataBinding().setActivity(this); }

    private void initView() {
        LocationSearchAdapter adapter = new LocationSearchAdapter();
        RecyclerView recyclerView = getDataBinding().rcContent;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        getPresenter().setAdapter(adapter);
        adapterView = adapter;

        Intent intent = this.getIntent();


        String callerActivity = intent.getStringExtra("ActivityID");
            if(callerActivity.equals("StoreEdit")) {
                adapterView.setOnViewHolderClickListener(new OnViewHolderClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        viewHolderClickedforOwneStoreEdit(position);
                    }
                });
            } else if(callerActivity.equals("CustomerMain")) {
                adapterView.setOnViewHolderClickListener(new OnViewHolderClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        viewHolderClickedForCustomerMain(position);
                    }
                });
            }

   }

   private void viewHolderClickedforOwneStoreEdit(int position) {
       Intent resultIntent = new Intent(LocationSearchActivity.this, StoreLocationActivity.class);
       resultIntent.putExtra("posXY", getPresenter().getMenuData(position));
       startActivity(resultIntent);
   }

    private void viewHolderClickedForCustomerMain(int position) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("posXY", getPresenter().getMenuData(position));
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    private void setUpSearchToolBar() {
        Toolbar toolbar = getDataBinding().searchBox.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle("");
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

        if(inputAddress.trim().length() == 0) {
            showToastMessage("값을 입력해주세요");
        } else {
            getPresenter().requestLocationSearch(inputAddress);
        }
    }
}
