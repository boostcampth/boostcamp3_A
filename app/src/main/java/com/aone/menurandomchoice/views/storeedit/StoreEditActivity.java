package com.aone.menurandomchoice.views.storeedit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import static com.aone.menurandomchoice.views.menupreview.MenuPreviewActivity.EXTRA_MENU_DETAIL_ITEM;
import static com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity.DEFAULT_LATITUDE;
import static com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity.DEFAULT_LONGITUDE;
import static com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity.EXTRA_MENU;
import static com.aone.menurandomchoice.views.ownerstore.OwnerStoreActivity.EXTRA_STORE;


public class StoreEditActivity extends BaseActivity<ActivityStoreEditBinding, StoreEditContract.View, StoreEditContract.Presenter>
implements  StoreEditContract.View{

    MapView mapView;
    ViewGroup mapViewContainer;

    public static final int REQUEST_CODE_LOCATIONSEARCH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initMapView();

        StoreDetail storeDetail = getIntent().getParcelableExtra(EXTRA_STORE);
        getDataBinding().setStoreDetail(storeDetail);
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

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
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
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    @Override
    public void moveToMenuEditPage(MenuDetail menuDetail) {
        Intent menuRegisterIntent = new Intent(StoreEditActivity.this, MenuRegisterActivity.class);
        menuRegisterIntent.putExtra(EXTRA_MENU, menuDetail);

        startActivityForResult(menuRegisterIntent, menuDetail.getSequence());
    }

    @Override
    public void moveToLocationSearchPage() {
        Intent locationSearchIntent = new Intent(StoreEditActivity.this, LocationSearchActivity.class);
        startActivityForResult(locationSearchIntent, REQUEST_CODE_LOCATIONSEARCH);
    }

    @Override
    public void setStartTimePickerDialog(String openTime) {
        if(openTime == null) {
            openTime = getResources().getString(R.string.activity_store_edit_default_starttime);
        }

        showTimePicker(openTime, "opentime");
    }

    @Override
    public void setEndTimePickerDialog(String closeTime) {
        if(closeTime == null) {
            closeTime = getResources().getString(R.string.activity_store_edit_default_endtime);
        }

        showTimePicker(closeTime, "closetime");
    }

    @Override
    public void showOpentimeChanged(String hour, String minute) {
        getDataBinding().getStoreDetail().setOpentime(hour + ":" + minute);
    }

    @Override
    public void showClosetimeChanged(String hour, String minute) {
        getDataBinding().getStoreDetail().setClosetime(hour + ":" + minute);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            MenuDetail menuDetail;
            switch (requestCode) {
                case 1:
                    menuDetail = data.getParcelableExtra(EXTRA_MENU_DETAIL_ITEM);
                    getDataBinding().getStoreDetail().getMenuList().set(0, menuDetail);
                    break;
                case 2:
                    menuDetail = data.getParcelableExtra(EXTRA_MENU_DETAIL_ITEM);
                    getDataBinding().getStoreDetail().getMenuList().set(1, menuDetail);
                    break;
                case 3:
                    menuDetail = data.getParcelableExtra(EXTRA_MENU_DETAIL_ITEM);
                    getDataBinding().getStoreDetail().getMenuList().set(2, menuDetail);
                    break;
                case REQUEST_CODE_LOCATIONSEARCH:
                    String address = data.getStringExtra("address");
                    Double longitude = data.getDoubleExtra("longitude", 0);
                    Double latitude = data.getDoubleExtra("latitude", 0);
                    setAddress(address, longitude, latitude);
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    public void initMapView() {
        mapViewContainer = getDataBinding().mapView;
        mapView = new MapView(this);

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        mapView.setMapCenterPoint(mapPoint, false);
        mapViewContainer.addView(mapView);

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setMapView(double latitude, double longitude, String name) {

        if(latitude == 0 && longitude == 0) {
            latitude = DEFAULT_LATITUDE;
            longitude = DEFAULT_LONGITUDE;
        }
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        mapView.setMapCenterPoint(mapPoint, false);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);

        final double finalLatitude = latitude;
        final double finalLongitude = longitude;
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void showTimePicker(String time, String type) {
        Bundle bundle = new Bundle();
        bundle.putString("time", time);
        bundle.putString("type", type);

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(bundle);
        timePickerFragment.setPresenter(getPresenter());
        timePickerFragment.show(getSupportFragmentManager(), "timepicker");

    }


    public void setAddress(String address, double latitude, double longitude) {
        getDataBinding().getStoreDetail().setAddress(address);
        getDataBinding().getStoreDetail().setLatitude(latitude);
        getDataBinding().getStoreDetail().setLongitude(longitude);

        String name = getDataBinding().getStoreDetail().getName();
        setMapView(latitude, longitude, name);
    }

    public void detachMapView() {
        mapViewContainer.removeView(mapView);
    }
}
