package com.aone.menurandomchoice.views.storeedit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityStoreEditBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.locationsearch.LocationSearchActivity;
import com.aone.menurandomchoice.views.menuregister.MenuRegisterActivity;
import com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import androidx.annotation.NonNull;

public class StoreEditActivity extends BaseActivity<ActivityStoreEditBinding, StoreEditContract.View, StoreEditContract.Presenter>
implements  StoreEditContract.View{

    public static final int REQUEST_CODE_LOCATION_SEARCH = 10;
    public static final String EXTRA_MENU_DETAIL_INFO = "EXTRA_MENU_DETAIL_INFO";
    private static final int REQUEST_CODE_MENU_REGISTER = 1;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
        setUpMapView();
        passedGetIntentToPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setUpMapView();
    }

    @Override
    protected void onPause() {
        super.onPause();

        detachMapView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_edit;
    }

    @NonNull
    @Override
    protected StoreEditContract.View getView() {
        return this;
    }

    @NonNull
    @Override
    protected StoreEditContract.Presenter setUpPresenter() {
        return new StoreEditPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu_store_edit_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.item_menu_store_edit_save:
                getPresenter().saveStoreDetail(getDataBinding().getStoreDetail());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_MENU_REGISTER:
                    attachViewToPresenter();
                    MenuDetail menuDetail = data.getParcelableExtra(MenuRegisterActivity.EXTRA_MENU_DETAIL_ITEM);
                    getPresenter().handlingReceivedMenuDetailData(menuDetail);
                    break;
                case REQUEST_CODE_LOCATION_SEARCH:
                    attachViewToPresenter();
                    String address = data.getStringExtra("address");
                    Double longitude = data.getDoubleExtra("longitude", 0);
                    Double latitude = data.getDoubleExtra("latitude", 0);
                    getPresenter().handlingReceivedMapInfo(address, longitude, latitude);
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpMapView() {
        if(mapView == null) {
            mapViewContainer = getDataBinding().mapView;
            mapView = new MapView(this);
            mapViewContainer.addView(mapView);

            mapView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    @Override
    public void showStoreDetailInfo(@NonNull StoreDetail storeDetail) {
        getDataBinding().setStoreDetail(storeDetail);
    }

    @Override
    public void moveToMenuEditPage(MenuDetail menuDetail) {
        Intent menuRegisterIntent = new Intent(StoreEditActivity.this, MenuRegisterActivity.class);
        menuRegisterIntent.putExtra(EXTRA_MENU_DETAIL_INFO, menuDetail);
        startActivityForResult(menuRegisterIntent, REQUEST_CODE_MENU_REGISTER);
    }

    @Override
    public void moveToLocationSearchPage() {
        Intent locationSearchIntent = new Intent(StoreEditActivity.this, LocationSearchActivity.class);
        startActivityForResult(locationSearchIntent, REQUEST_CODE_LOCATION_SEARCH);
    }

    @Override
    public void showStartTimePickerDialog(@NonNull String openTime) {
        showTimePicker(openTime, "opentime");
    }

    @Override
    public void showEndTimePickerDialog(@NonNull String closeTime) {
        showTimePicker(closeTime, "closetime");
    }

    @Override
    public void showChangedOpenTime(@NonNull String openTime) {
        getDataBinding().getStoreDetail().setOpentime(openTime);
    }

    @Override
    public void showChangedCloseTime(@NonNull String closeTime) {
        getDataBinding().getStoreDetail().setClosetime(closeTime);
    }

    @Override
    public void setMenuDetailToDataBinding(@NonNull MenuDetail menuDetail) {
        int menuDetailIndex = menuDetail.getSequence() - 1;
        getDataBinding().getStoreDetail().getMenuList().set(menuDetailIndex, menuDetail);
    }

    @NonNull
    @Override
    public String getInputtedStoreName() {
        return getDataBinding().activityStoreEditStoreNameEt.getText().toString();
    }

    @NonNull
    @Override
    public String getInputtedDescription() {
        return getDataBinding().activityStoreEditDescriptionEt.getText().toString();
    }

    @Override
    public void moveCenterToMap(@NonNull MapPoint centerPoint) {
        mapView.setMapCenterPoint(centerPoint, false);
    }

    @Override
    public void showStoreMakerToMap(@NonNull MapPOIItem marker) {
        mapView.addPOIItem(marker);
    }

    @Override
    public void setMapAddress(@NonNull String address) {
        getDataBinding().getStoreDetail().setAddress(address);
    }

    private void passedGetIntentToPresenter() {
        StoreDetail storeDetail = getIntent().getParcelableExtra(OwnerStoreActivity.EXTRA_STORE);
        getPresenter().handlingReceivedStoreDetail(storeDetail);
    }

    private void detachMapView() {
        mapViewContainer.removeView(mapView);
        mapView = null;
    }

    private void showTimePicker(String time, String type) {
        Bundle bundle = new Bundle();
        bundle.putString("time", time);
        bundle.putString("type", type);

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(bundle);
        timePickerFragment.setPresenter(getPresenter());
        timePickerFragment.show(getSupportFragmentManager(), "timepicker");
    }

}
