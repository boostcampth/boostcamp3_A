package com.aone.menurandomchoice.views.ownerstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerStoreBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.menupreview.MenuPreviewActivity;
import com.aone.menurandomchoice.views.storeedit.StoreEditActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;

import androidx.annotation.NonNull;

public class OwnerStoreActivity
        extends BaseActivity<ActivityOwnerStoreBinding, OwnerStoreContract.View, OwnerStoreContract.Presenter>
        implements OwnerStoreContract.View {

    public static final String EXTRA_MENU = "EXTRA_MENU";
    public static final String EXTRA_STORE = "EXTRA_STORE";
    public static final String EXTRA_USER_ACCESS_INFO = "EXTRA_USER_ACCESS_INFO";

    public static final double DEFAULT_LATITUDE = 37.5514579595;
    public static final double DEFAULT_LONGITUDE = 126.951949155;

    private ViewGroup mapViewContainer;
    private MapView mapView;
    private boolean isOwner;
    private int storeIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
        getParcelData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initMapView();
        getPresenter().loadStoreDetail(storeIdx);
    }


    @Override
    protected void onPause() {
        super.onPause();

        detachMapView();
    }


    @Override
    protected void onStop() {
        super.onStop();

        getPresenter().stopNetwork();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_action_bar, menu);

        MenuItem edit = menu.findItem(R.id.item_action_bar_edit);
        if(isOwner) {
             setLogoutVisible(true);
             edit.setVisible(true);
        } else {
            setLogoutVisible(false);
             edit.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.item_action_bar_edit:
                moveToOwnerEditPage(getDataBinding().getStoreDetail());
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

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_owner_store;
    }

    @NonNull
    @Override
    protected OwnerStoreContract.Presenter setUpPresenter() {
        return new OwnerStorePresenter();
    }

    @NonNull
    @Override
    protected OwnerStoreContract.View getView() {
        return this;
    }

    @Override
    public void moveToOwnerEditPage(StoreDetail storeDetail) {
        Intent storeEditIntent = new Intent(OwnerStoreActivity.this, StoreEditActivity.class);
        storeEditIntent.putExtra(EXTRA_STORE, storeDetail);
        startActivity(storeEditIntent);
    }

    @Override
    public void moveToMenuPreviewPage(MenuDetail menuDetail) {
        Intent menuPreviewIntent = new Intent(this, MenuPreviewActivity.class);
        menuPreviewIntent.putExtra(MenuPreviewActivity.EXTRA_MENU_DETAIL_ITEM, menuDetail);
        startActivity(menuPreviewIntent);
    }

    @Override
    public void finishOwnerStorePage() {
        finish();
    }

    @Override
    public void moveToMapDetailPage(double latitude, double longitude) {
        /*
        Intent mapDetailIntent = new Intent(OwnerStoreActivity.this, MapDetailActivity.class);
        mapDetailIntent.putExtra("latitude", latitude);
        mapDetailIntent.putExtra("longitude", longitude);
        startActivity(mapDetailIntent);
        */
    }

    @Override
    public void showStoreDetail(StoreDetail storeDetail) {
        UserAccessInfo userAccessInfo = getIntent().getParcelableExtra(EXTRA_USER_ACCESS_INFO);

        storeDetail.setStoreIdx(userAccessInfo.getAccessStoreIndex());

        List<MenuDetail> list = storeDetail.getMenuList();
        for(int i=0; i<list.size(); i++) {
            list.get(i).setStoreIdx(userAccessInfo.getAccessStoreIndex());
        }

        storeDetail.setMenuList(list);

        getDataBinding().setStoreDetail(storeDetail);
        getDataBinding().getStoreDetail().setStoreIdx(storeIdx);

        setMapView(storeDetail.getLatitude(), storeDetail.getLongitude(), storeDetail.getName());
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
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    moveToMapDetailPage(finalLatitude, finalLongitude);
                    return true;
                }
                return true;
            }
        });
    }


    public void detachMapView() {
        mapViewContainer.removeView(mapView);
    }

    public void getParcelData() {
        UserAccessInfo userAccessInfo = getIntent().getParcelableExtra(EXTRA_USER_ACCESS_INFO);

        storeIdx = userAccessInfo.getAccessStoreIndex();
        isOwner = userAccessInfo.isOwner();
    }

    public void setLogoutVisible(boolean isOwner) {
        if(isOwner) {
            getDataBinding().activityOwnerStoreLogout.setVisibility(View.VISIBLE);
        } else {
            getDataBinding().activityOwnerStoreLogout.setVisibility(View.GONE);
        }
    }
}
