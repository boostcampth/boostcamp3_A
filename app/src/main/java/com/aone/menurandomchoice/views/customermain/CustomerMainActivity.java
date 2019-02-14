package com.aone.menurandomchoice.views.customermain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityCustomerMainBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.locationsearch.LocationSearchActivity;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapter;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import com.aone.menurandomchoice.views.menuselect.MenuSelectActivity;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CustomerMainActivity extends BaseActivity<ActivityCustomerMainBinding, CustomerMainContract.View, CustomerMainContract.Presenter>
        implements CustomerMainContract.View, MapView.MapViewEventListener {

    private static final String LOG_TAG = "CustomMainActivity";

    private MapPOIItem mCustomMarker;
    private MapPoint CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.4020737, 127.1086766);

    private MapCircle circle = new MapCircle(
            MapPoint.mapPointWithGeoCoord(CUSTOM_MARKER_POINT.getMapPointGeoCoord().latitude, CUSTOM_MARKER_POINT.getMapPointGeoCoord().longitude), // center
            50, // radius
            Color.argb(0, 255, 120, 120), // strokeColor
            Color.argb(128, 255, 120, 120) // fillColor
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityToDataBinding();
        setUpSearchToolBar();
        setUpCategoryRecyclerView();

        getDataBinding().activityCustomerMainMvDaum.setMapViewEventListener(this);
        createCustomMarker(getDataBinding().activityCustomerMainMvDaum);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchBlock() {
        getDataBinding().activityCustomerMainMvDaum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }});
    }

    private void createCustomMarker(MapView mapView) {
        mCustomMarker = new MapPOIItem();
        String name = "JMT";
        mCustomMarker.setItemName(name);
        mCustomMarker.setTag(1);
        mCustomMarker.setMapPoint(CUSTOM_MARKER_POINT);

        mCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        mCustomMarker.setCustomImageResourceId(R.drawable.custom_marker_red);
        mCustomMarker.setCustomImageAutoscale(false);
        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(mCustomMarker);
        mapView.selectPOIItem(mCustomMarker, true);
        mapView.setMapCenterPoint(CUSTOM_MARKER_POINT, false);

    }

    private void setUpActivityToDataBinding() {
        getDataBinding().setActivity(this);
    }

    private void setUpSearchToolBar() {
        Toolbar toolbar = getDataBinding().toolbar;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.setMapCenterPointAndZoomLevel(CUSTOM_MARKER_POINT, 1, true);
        Log.i(LOG_TAG, "onMapViewInitialized");
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
        mapView.removeAllCircles();
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        circle.setCenter(mapPoint);
        mapView.addCircle(circle);
        mCustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) { }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) { }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) { }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) { }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) { }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) { }

    @Override
    protected int getLayoutId() { return R.layout.activity_customer_main; }

    @NonNull
    @Override
    protected CustomerMainContract.Presenter setUpPresenter() { return new CustomerMainPresenter(); }

    @Override
    protected CustomerMainContract.View getView() { return this; }

    private void setUpCategoryRecyclerView() {
        MenuCategoryAdapter menuCategoryAdapter = new MenuCategoryAdapter();
        menuCategoryAdapter.setItems(createMenuCategoryItems());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        getDataBinding().activityCustomerMainRcCategory.setLayoutManager(layoutManager);
        getDataBinding().activityCustomerMainRcCategory.setAdapter(menuCategoryAdapter);
    }

    private List<MenuCategoryItem> createMenuCategoryItems() {
        String[] categories = getResources().getStringArray(R.array.category);
        ArrayList<MenuCategoryItem> menuCategoryItems = new ArrayList<>();
        for(String category : categories) {
            menuCategoryItems.add(new MenuCategoryItem(category));
        }
        return menuCategoryItems;
    }

    public void moveToLocationSearchPage() {
        getDataBinding().activityCustomerMainMvDaum.removeAllCircles();
        Intent locationSearchIntent = new Intent(CustomerMainActivity.this, LocationSearchActivity.class);
        startActivityForResult(locationSearchIntent,3000);
    }

    public void moveToMenuSelectPage() {
        getDataBinding().activityCustomerMainMvDaum.removeAllCircles();
        Intent menuSelectIntent = new Intent(CustomerMainActivity.this, MenuSelectActivity.class);
        startActivity(menuSelectIntent);
    }

    @Override
    public void onRadiusButtonClicked(View view) {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 3000:
                    Bundle posXY = data.getBundleExtra("posXY");
                    if( posXY == null ) {
                        showToastMessage("좌표 값이 없습니다.");
                        return;
                    }
                    getDataBinding().activityCustomerMainMvDaum.setMapCenterPointAndZoomLevel(
                            MapPoint.mapPointWithGeoCoord(posXY.getDouble("latitude"), posXY.getDouble("longitude")),
                            1,
                            true);
                    break;
            }
        }
    }

}
