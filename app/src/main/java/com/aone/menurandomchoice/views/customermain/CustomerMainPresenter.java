package com.aone.menurandomchoice.views.customermain;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.mapper.MenuMapper;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.utils.NetworkUtil;
import com.aone.menurandomchoice.utils.StringUtil;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class CustomerMainPresenter extends BasePresenter<CustomerMainContract.View>
    implements CustomerMainContract.Presenter {

    private static final String LOG_TAG = "CustomerMainPresenter";

    private MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel;
    private List<MenuLocation> menuList;

    public void requestMenuList(final double latitude, final double longitude, final double radius) {
        getRepository().requestMenuLocation(MenuMapper.createRequestLocationQueryMap(latitude,longitude),
                new NetworkResponseListener<List<MenuLocation>>() {
                    @Override
                    public void onReceived(@NonNull List<MenuLocation> response) {
                        if(isAttachView()) {
                            menuList = response;
                            if(response.size() > 0) {
                                getMenuCountFiltered(latitude, longitude, radius);
                            } else {
                                getView().setMarkerAtNewLocation(latitude, longitude, null);
                                sendMessageToView(R.string.activity_customer_main_empty_result);
                            }
                        }
                    }

                    @Override
                    public void onError(JMTErrorCode errorCode) {
                        getMenuCountFiltered(latitude, longitude, radius);
                        Log.d(LOG_TAG, StringUtil.getString(R.string.activity_customer_main_response_error));
                    }
        });
    }

    public String getSelectedCategory() {
        return menuCategoryAdapterModel.getSelectedCategory();
    }

    public void getMenuCountFiltered(double centerLat, double centerLon, double radius) {

        String category = "";
        int len = 0;
        double distance;
        List<MenuLocation> closerDistanceList = new ArrayList<>();
        MenuLocation menuLocation;

        if(menuList != null) {
            len = menuList.size();
            category = menuCategoryAdapterModel.getSelectedCategory();
        }

        for (int i = 0; i < len; i++) {
            menuLocation = menuList.get(i);
            if(((StringUtil.getString(R.string.activity_customer_main_all_category)).equals(category)
                    && !((menuLocation.getCategory()).equals("")) )
                    || (menuLocation.getCategory()).equals(category)) {
                distance = LocationDistance.distance(centerLat
                                                        , centerLon
                                                        , menuLocation.getLatitude()
                                                        , menuLocation.getLongitude()
                                                        , 1);
                if (radius >= distance) {
                    closerDistanceList.add(menuLocation);
                }
            }
        }
        getView().setMarkerAtNewLocation(centerLat, centerLon, closerDistanceList);
    }

    @Override
    public void setAdapterModel(@NonNull MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel) {
        this.menuCategoryAdapterModel = menuCategoryAdapterModel;
        this.menuCategoryAdapterModel.setItems(createMenuCategoryItems());
        this.menuCategoryAdapterModel.selectDefaultCategory();
    }


    @Override
    public void handlingMenuCategoryItemClick(int position) {
        menuCategoryAdapterModel.setSelectedPositionOfMenuCategories(position);
        getView().updateMapByRadiusCategory();
    }

    private List<MenuCategoryItem> createMenuCategoryItems() {
        ArrayList<MenuCategoryItem> menuCategoryItems = new ArrayList<>();
        String[] categories = getView().getAppContext().getResources().getStringArray(R.array.category);

        for(String category : categories) {
            menuCategoryItems.add(new MenuCategoryItem(category));
        }

        return menuCategoryItems;
    }

    public void stopNetwork() {
        getRepository().cancelAll();
    }

    public void handlingGPSButtonClicked() {
        checkPermissionWithTedPermission();
    }

    private void checkPermissionWithTedPermission() {
        if(isAttachView()) {

            if(!NetworkUtil.isNetworkConnecting()) {
                Toast.makeText(getView().getActivityContext(),"Check Network", Toast.LENGTH_SHORT).show();
                return;
            }

            LocationManager locationManager = (LocationManager) getView().getActivityContext().getSystemService(Context.LOCATION_SERVICE);
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Toast.makeText(getView().getActivityContext(),"turn on the gps", Toast.LENGTH_SHORT).show();
                return;
            }

            TedPermission.with(getView().getAppContext())
                    .setRationaleMessage(StringUtil.getString(R.string.permission_GPS_request_guide))
                    .setDeniedMessage(StringUtil.getString(R.string.permission_GPS_denied_guide))
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                    .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            getView().onGPSButtonClicked();
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            getView().showToastMessage("GPS permission needed");
                        }
                    })
                    .check();
        }
    }
}
