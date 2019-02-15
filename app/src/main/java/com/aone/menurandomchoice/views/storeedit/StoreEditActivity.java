package com.aone.menurandomchoice.views.storeedit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityStoreEditBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import androidx.annotation.NonNull;


public class StoreEditActivity extends BaseActivity<ActivityStoreEditBinding, StoreEditContract.View, StoreEditContract.Presenter>
implements  StoreEditContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();

        //initMapView(getDataBinding().mapView);
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
    public void moveToMenuEditPage() {
    }

    @Override
    public void moveToLocationSearchPage() {
    }

    public void initMapView(MapView mapView) {

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.5514579595, 126.951949155);
        mapView.setMapCenterPoint(mapPoint, false);

    }

}
