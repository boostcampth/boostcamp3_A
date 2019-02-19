package com.aone.menurandomchoice.views.storeedit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityStoreEditBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.locationsearch.LocationSearchActivity;
import com.aone.menurandomchoice.views.menuregister.MenuRegisterActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import androidx.annotation.NonNull;


public class StoreEditActivity extends BaseActivity<ActivityStoreEditBinding, StoreEditContract.View, StoreEditContract.Presenter>
implements  StoreEditContract.View, MapView.MapViewEventListener {

    private MapView mMapView;

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();

        /**
         * This code is sample code for menu register activity
         * please remove this code later.
         *
         */


        getDataBinding().activityStoreEditLlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sampleIntent = new Intent(StoreEditActivity.this, MenuRegisterActivity.class);
                startActivity(sampleIntent);
            }
        });

        getDataBinding().tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sampleIntent = new Intent(StoreEditActivity.this, LocationSearchActivity.class);
                sampleIntent.putExtra("ActivityID","StoreEdit");
                startActivity(sampleIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMapView = new MapView(this);
        getDataBinding().mapView.addView(mMapView);

        mMapView.setMapViewEventListener(this);
        mMapView.setMapType(MapView.MapType.Standard);

        MapPOIItem mDefaultMarker = new MapPOIItem();
        mDefaultMarker.setItemName("");
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);

        Bundle posXY = getIntent().getBundleExtra("posXY");
        MapPoint DEFAULT_MARKER_POINT;
        if( posXY != null ) {
            DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(posXY.getDouble("latitude"), posXY.getDouble("longitude"));
        } else {
            DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.4980854357918, 127.028000275071);
        }

        mDefaultMarker.setMapPoint(DEFAULT_MARKER_POINT);
        mMapView.addPOIItem(mDefaultMarker);
        mMapView.setMapCenterPoint(DEFAULT_MARKER_POINT, true);

  }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        getDataBinding().mapView.removeView(mMapView);
    }

    @Override
    protected void onStop() {
        super.onStop();

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

}
