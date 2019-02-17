package com.aone.menurandomchoice.views.customermain;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.mapper.MenuMapper;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class CustomerMainPresenter extends BasePresenter<CustomerMainContract.View>
    implements CustomerMainContract.Presenter {

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
                                getView().showToastMessage("결과값이 없습니다");
                            }
                        }
                    }

                    @Override
                    public void onError() {

                    }
        });
    }

    public void getMenuCountFiltered(double centerLat, double centerLon, double radius) {

        String category = menuCategoryAdapterModel.getSelectedCategory();
        int len = menuList.size();
        double distance;
        List<MenuLocation> closerDistanceList = new ArrayList<>();
        MenuLocation menuLocation;

        for (int i = 0; i < len; i++) {
            menuLocation = menuList.get(i);
            if(category.equals("전체") || category.equals(menuLocation.getCategory())) {
                distance = LocationDistance.distance(centerLat
                                                        , centerLon
                                                        , menuLocation.getLatitude()
                                                        , menuLocation.getLongitude()
                                                        , "meter");
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
}
