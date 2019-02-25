package com.aone.menurandomchoice.views.customermain;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPoint;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

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
        if(fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
            fusedLocationClient = null;
        }
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
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Toast.makeText(getView().getActivityContext(),"Turn on the GPS", Toast.LENGTH_SHORT).show();
                return;
            }


            if (ActivityCompat.checkSelfPermission(getView().getActivityContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&  ActivityCompat.checkSelfPermission(getView().getActivityContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getView().requestPermission();
                return;
            }

            startRequestGPS();

        }
    }

    private void startRequestGPS() {
        Log.d("showGPS","start");
        if(fusedLocationClient == null) {
            Log.d("showGPS","init");
            getView().startGPSAnimation();
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(getView().getActivityContext());
            requestGPS(fusedLocationClient);
        } else {
            Log.d("showGPS","progressing");
        }
    }

    private FusedLocationProviderClient fusedLocationClient;

    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            fusedLocationClient.removeLocationUpdates(this);
            fusedLocationClient = null;
            getView().stopGPSAnimation();

            Log.d("requestGPS", "result");
            if (locationResult == null) {
                Log.d("requestGPS", "fail");
                return;
            }
            for (Location location : locationResult.getLocations()) {
                Log.d("requestGPS", "success");
                if(isAttachView()) {
                    getView().successGPS(location.getLatitude(), location.getLongitude());
                }
            }
        }
    };

    private void requestGPS(final FusedLocationProviderClient fusedLocationClient) {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(getView().getActivityContext());
        client.checkLocationSettings(builder.build());

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}
