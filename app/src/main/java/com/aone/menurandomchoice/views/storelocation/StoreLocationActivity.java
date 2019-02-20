package com.aone.menurandomchoice.views.storelocation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityStoreLocationBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.storeedit.StoreEditActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import androidx.annotation.Nullable;

public class StoreLocationActivity
        extends BaseActivity<ActivityStoreLocationBinding, StoreLocationContract.View, StoreLocationContract.Presenter>
        implements StoreLocationContract.View , MapView.MapViewEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener{

    static final private String FAIL_GEO = "주소를 찾을 수 없는 지역입니다";
    private MapPOIItem mDefaultMarker;
    private MapPoint DEFAULT_MARKER_POINT;
    private MapView mMapView;
    private String address;

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding(FAIL_GEO);
    }

    private void onFinishReverseGeoCoding(String result) {
        address = result;
        mDefaultMarker.setItemName(result);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityToDataBinding();
    }

    private void setUpActivityToDataBinding() {
        getDataBinding().setActivity(this);
    }

    public void onConfirmButtonClicked() {
        Intent resultIntent = new Intent(this, StoreEditActivity.class);
        Bundle posXY = new Bundle();
        posXY.putDouble("longitude", mMapView.getMapCenterPoint().getMapPointGeoCoord().longitude);
        posXY.putDouble("latitude", mMapView.getMapCenterPoint().getMapPointGeoCoord().latitude);
        posXY.putString("address", address);
        resultIntent.putExtra("posXY", posXY);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMapView = new MapView(this);

        mMapView.setMapViewEventListener(this);
        mMapView.setMapType(MapView.MapType.Standard);

        getDataBinding().mapView.addView(mMapView);

        Intent intent = getIntent();
        Bundle posXY = intent.getBundleExtra("posXY");

        if(posXY != null) {
            DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(posXY.getDouble("latitude")
                    , posXY.getDouble("longitude"));
        } else {
            DEFAULT_MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.4980854357918, 127.028000275071);
        }

        mDefaultMarker = new MapPOIItem();
        String name = "";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setMapPoint(DEFAULT_MARKER_POINT);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);

        mMapView.addPOIItem(mDefaultMarker);
        mMapView.selectPOIItem(mDefaultMarker, true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        getDataBinding().mapView.removeView(mMapView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_location;
    }

    @Override
    protected StoreLocationContract.Presenter setUpPresenter() {
        return new StoreLocationPresenter();
    }

    @Override
    protected StoreLocationContract.View getView() {
        return this;
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.setMapCenterPointAndZoomLevel(DEFAULT_MARKER_POINT, 1, true);
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        mDefaultMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude));
        MapReverseGeoCoder mReverseGeoCoder = new MapReverseGeoCoder("6f504f9b73ad280372b2aff0036b6f32"
                , mMapView.getMapCenterPoint()
                , StoreLocationActivity.this
                , StoreLocationActivity.this);
        mReverseGeoCoder.startFindingAddress();
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
}
