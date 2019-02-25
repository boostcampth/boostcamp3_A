package com.aone.menurandomchoice.views.customermain;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityCustomerMainBinding;
import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.model.MenuLocationCamera;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.utils.ClickUtil;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.locationsearch.LocationSearchActivity;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapter;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.viewholder.OnMenuCategoryClickListener;
import com.aone.menurandomchoice.views.menuselect.MenuSelectActivity;
import com.google.android.gms.location.FusedLocationProviderClient;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CustomerMainActivity extends BaseActivity<ActivityCustomerMainBinding, CustomerMainContract.View, CustomerMainContract.Presenter>
        implements CustomerMainContract.View, MapView.MapViewEventListener {

    private final int LOCATION_DATA = 3000;

    private List<View> radiusButton = new ArrayList<>();
    private MenuCategoryAdapterContract.View menuCategoryAdapterView;
    private MapView mMapView;
    private MapPOIItem mCustomMarker;
    private MapCircle circle;
    private double radius;
    private AnimationDrawable frameAnimation;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityToDataBinding();
        setUpPresenterToDataBinding();
        setUpSearchToolBar();
        setRadiusButtonList();
        setUpCategoryRecyclerView();

        radius = 50;
        getDataBinding().setMenuLocationCamera(new MenuLocationCamera(37.4980854357918
                                                                        ,127.028000275071
                                                                        ,2));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMapView = new MapView(this);
        mMapView.setMapViewEventListener(this);
        mMapView.setMapType(MapView.MapType.Standard);

        getDataBinding().activityCustomerMainMvDaum.addView(mMapView);

        createCustomMarker();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.removeAllPOIItems();
        getDataBinding().getMenuLocationCamera().setLatitude(mMapView.getMapCenterPoint()
                                                                        .getMapPointGeoCoord()
                                                                        .latitude);
        getDataBinding().getMenuLocationCamera().setLongitude(mMapView.getMapCenterPoint()
                                                                        .getMapPointGeoCoord()
                                                                        .longitude);
        getDataBinding().getMenuLocationCamera().setZoom(mMapView.getZoomLevel());
        getDataBinding().activityCustomerMainMvDaum.removeView(mMapView);

        fusedLocationClient = null;
    }

    private void createCustomMarker() {

        MenuLocationCamera menuLocationCamera = getDataBinding().getMenuLocationCamera();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(menuLocationCamera.getLatitude()
                , menuLocationCamera.getLongitude());

        mCustomMarker = new MapPOIItem();
        mCustomMarker.setItemName(getString(R.string.app_name));
        mCustomMarker.setMapPoint(mapPoint);

        mCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        mCustomMarker.setCustomImageResourceId(R.drawable.custom_marker_chicken);
        mCustomMarker.setCustomImageAutoscale(false);
        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        circle = new MapCircle(
                MapPoint.mapPointWithGeoCoord(mapPoint.getMapPointGeoCoord().latitude
                                            , mapPoint.getMapPointGeoCoord().longitude), // center
                (int)radius, // radius
                Color.argb(0, 255, 120, 120), // strokeColor
                Color.argb(128, 255, 120, 120) // fillColor
        );

        circle.setCenter(mapPoint);
        circle.setRadius((int)radius);
        mMapView.addCircle(circle);

        mMapView.setMapCenterPointAndZoomLevel(mapPoint
                                                , getDataBinding().getMenuLocationCamera().getZoom()
                                                , false);
    }

    private void setUpActivityToDataBinding() {
        getDataBinding().setActivity(this);
    }

    private void setUpPresenterToDataBinding() { getDataBinding().setPresenter(getPresenter()); }

    private void setUpSearchToolBar() {
        Toolbar toolbar = getDataBinding().toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setRadiusButtonList() {
        radiusButton.add(getDataBinding().activityCustomerMainIvRadius1);
        radiusButton.add(getDataBinding().activityCustomerMainIvRadius2);
        radiusButton.add(getDataBinding().activityCustomerMainIvRadius3);
        radiusButton.add(getDataBinding().activityCustomerMainIvRadius4);
        radiusButton.add(getDataBinding().activityCustomerMainIvRadius5);
        getDataBinding().activityCustomerMainIvRadius1.setSelected(true);
    }

    private void setUpCategoryRecyclerView() {
        MenuCategoryAdapter menuCategoryAdapter = new MenuCategoryAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this
                                                                    , LinearLayoutManager.HORIZONTAL
                                                                    , false);
        getDataBinding().activityCustomerMainRcCategory.setLayoutManager(layoutManager);
        getDataBinding().activityCustomerMainRcCategory.setAdapter(menuCategoryAdapter);

        setUpAdapterToPresenter(menuCategoryAdapter);
        setUpAdapterToThis(menuCategoryAdapter);
    }

    private void setUpAdapterToPresenter(MenuCategoryAdapter menuCategoryAdapter) {
        getPresenter().setAdapterModel(menuCategoryAdapter);
    }

    private void setUpAdapterToThis(MenuCategoryAdapter menuCategoryAdapter) {
        menuCategoryAdapterView = menuCategoryAdapter;
        menuCategoryAdapterView.setOnMenuCategoryClickListener(new OnMenuCategoryClickListener() {
            @Override
            public void onMenuCategoryItemClick(@NonNull View view, int position) {
                getPresenter().handlingMenuCategoryItemClick(position);
            }
        });
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                0);

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 0) {
            if(grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
               getPresenter().handlingGPSButtonClicked();
            } else {
                Log.d("PERMISSION","DENIED..");
            }
        }
    }

    public void successGPS(double latitude, double longitude) {
        mMapView.moveCamera(CameraUpdateFactory
                .newMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude),
                        2));
    }

    public void startGPSAnimation() {
        getDataBinding().imgGPS.setImageResource(0);
        getDataBinding().imgGPS.setBackgroundResource(R.drawable.gps_frame_animation_list);
        frameAnimation = (AnimationDrawable) getDataBinding().imgGPS.getBackground();
        frameAnimation.start();

    }

    public void stopGPSAnimation() {
        frameAnimation.stop();
        getDataBinding().imgGPS.setImageResource(R.drawable.ic_gpsborder);
        getDataBinding().imgGPS.setBackgroundResource(0);
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
    public void onMapViewInitialized(MapView mapView) { }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) { }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        requestNewMenuList(mapPoint);
    }

    private void requestNewMenuList(MapPoint mapPoint) {
        mMapView.removeAllCircles();
        circle.setCenter(mapPoint);
        mMapView.addCircle(circle);
        mMapView.removeAllPOIItems();

        getPresenter().requestMenuList(mapPoint.getMapPointGeoCoord().latitude
                                        , mapPoint.getMapPointGeoCoord().longitude
                                        , radius);
    }

    public void updateMapByRadiusCategory() {
        mMapView.removeAllPOIItems();
        MapPoint.GeoCoordinate mapPointGeo = mMapView.getMapCenterPoint().getMapPointGeoCoord();
        getPresenter().getMenuCountFiltered(mapPointGeo.latitude, mapPointGeo.longitude, radius);
    }

    @Override
    public void onRadiusButtonClicked(View view) {
        View radiusView;
        for(int i =0; i < radiusButton.size(); i++) {
            radiusView = radiusButton.get(i);
            radiusView.setSelected(false);
            if(radiusView == view ) {
                radiusView.setSelected(true);
                radius = Double.valueOf((String)radiusView.getTag());
            }
        }

        mMapView.removeAllCircles();
        circle.setRadius((int)radius);
        mMapView.addCircle(circle);

        updateMapByRadiusCategory();
    }

    public void setMarkerAtNewLocation(double lat, double lon, List<MenuLocation> closerDistanceList) {
        int len = 0;
        if(closerDistanceList != null) {
            len = closerDistanceList.size();
        }

        mCustomMarker.setItemName(len+"");
        mCustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
        mMapView.addPOIItem(mCustomMarker);
        mMapView.selectPOIItem(mCustomMarker, true);

        MapPOIItem closerMenu;
        for(int i = 0; i < len; i++) {
            closerMenu = new MapPOIItem();
            closerMenu.setItemName("");
            closerMenu.setMapPoint(MapPoint.mapPointWithGeoCoord(closerDistanceList.get(i).getLatitude()
                                                                , closerDistanceList.get(i).getLongitude()));
            closerMenu.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            closerMenu.setCustomImageResourceId(R.drawable.custom_marker_pin);
            closerMenu.setCustomImageAutoscale(false);
            closerMenu.setCustomImageAnchor(0.5f, 1.0f);
            closerMenu.setShowCalloutBalloonOnTouch(false);
            mMapView.addPOIItem(closerMenu);
        }

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

    public void moveToLocationSearchPage(View view) {
        ClickUtil.preventDuplicateClick(view);

        mMapView.removeAllCircles();

        Intent locationSearchIntent = new Intent(CustomerMainActivity.this
                                                , LocationSearchActivity.class);
        locationSearchIntent.putExtra(getString(R.string.activity_store_edit_request_location_search)
                                    , getString(R.string.activity_customer_main_descriptor));
        startActivityForResult(locationSearchIntent,LOCATION_DATA);
    }

    public void moveToMenuSelectPage(View view) {
        ClickUtil.preventDuplicateClick(view);
        if(mMapView.getPOIItems().length > 1) {
            mMapView.removeAllCircles();
            Intent menuSelectIntent = new Intent(CustomerMainActivity.this
                                                , MenuSelectActivity.class);
            String category = getPresenter().getSelectedCategory();

            MapPoint.GeoCoordinate mapPointGeo = mMapView.getMapCenterPoint().getMapPointGeoCoord();
            menuSelectIntent.putExtra(getString(R.string.activity_customer_main_extra_menu_data)
                                    , new MenuSearchRequest(mapPointGeo.latitude
                                                            , mapPointGeo.longitude
                                                            , (int)radius
                                                            , category));

            startActivity(menuSelectIntent);
        } else {
            showToastMessage(getString(R.string.activity_customer_main_request_error));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case LOCATION_DATA:
                    setIntent(data);
                    Bundle posXY = data.getBundleExtra(getView().getActivityContext().getString(R.string.activity_customer_main_xy));
                    if( posXY == null ) {
                        showToastMessage(getView().getActivityContext().getString(R.string.activity_customer_main_empty_result));
                        return;
                    }
                    getDataBinding().getMenuLocationCamera().setLatitude(posXY.getDouble(getView().getActivityContext().getString(R.string.activity_customer_main_latitude)));
                    getDataBinding().getMenuLocationCamera().setLongitude(posXY.getDouble(getView().getActivityContext().getString(R.string.activity_customer_main_longitude)));
            }
        }
    }

}
