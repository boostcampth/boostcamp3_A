package com.aone.menurandomchoice.views.storeedit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import com.aone.menurandomchoice.views.base.widget.RappingTextWatcher;
import com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import androidx.annotation.NonNull;

public class StoreEditActivity
        extends BaseActivity<ActivityStoreEditBinding, StoreEditContract.View, StoreEditContract.Presenter>
        implements StoreEditContract.View {

    public static final String EXTRA_MENU_DETAIL_INFO = "EXTRA_MENU_DETAIL_INFO";

    public static final int REQUEST_CODE_LOCATION_SEARCH = 10;
    private static final int REQUEST_CODE_MENU_REGISTER = 1;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
        setUpMapView();
        setUpEditTextChangeListener();
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

                     //Todo  위치 검색 기능 후 하드로 박혀있는 문자열 들 상수 처리 해야함
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

    private void setUpEditTextChangeListener() {
        getDataBinding().activityStoreEditNameEt.addTextChangedListener(new RappingTextWatcher(){
            @Override
            public void afterTextChanged(Editable editable) {
                StoreDetail storeDetail = getDataBinding().getStoreDetail();
                if(storeDetail != null) {
                    storeDetail.setName(editable.toString());
                }
            }
        });

        getDataBinding().activityStoreEditDescriptionEt.addTextChangedListener(new RappingTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                StoreDetail storeDetail = getDataBinding().getStoreDetail();
                if(storeDetail != null) {
                    storeDetail.setDescription(editable.toString());
                }
            }
        });
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
        showTimePicker(openTime, TimePickerFragment.OPEN);
    }

    @Override
    public void showEndTimePickerDialog(@NonNull String closeTime) {
        showTimePicker(closeTime, TimePickerFragment.CLOSE);
    }

    @Override
    public void showChangedOpenTime(@NonNull String openTime) {
        getDataBinding().activityStoreEditOpenTimeTv.setText(openTime);
        StoreDetail storeDetail = getDataBinding().getStoreDetail();
        if(storeDetail != null) {
            storeDetail.setOpentime(openTime);
        }
    }

    @Override
    public void showChangedCloseTime(@NonNull String closeTime) {
        getDataBinding().activityStoreEditCloseTimeTv.setText(closeTime);
        StoreDetail storeDetail = getDataBinding().getStoreDetail();
        if(storeDetail != null) {
            storeDetail.setClosetime(closeTime);
        }
    }

    @NonNull
    @Override
    public StoreDetail getInputtedStoreDetail() {
        return getDataBinding().getStoreDetail();
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

    @Override
    public void viewFinish() {
        finish();
    }

    private void passedGetIntentToPresenter() {
        StoreDetail storeDetail = getIntent().getParcelableExtra(OwnerStoreActivity.EXTRA_STORE);
        getPresenter().handlingReceivedStoreDetail(storeDetail);
    }

    private void detachMapView() {
        mapViewContainer.removeView(mapView);
        mapView = null;
    }

    private void showTimePicker(String time, int type) {
        Bundle bundle = new Bundle();
        bundle.putString(TimePickerFragment.BUNDLE_PICKER_TIME, time);
        bundle.putInt(TimePickerFragment.BUNDLE_PICKER_TYPE, type);

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(bundle);
        timePickerFragment.setPresenter(getPresenter());
        timePickerFragment.show(getSupportFragmentManager(), "timepicker");
    }

}
