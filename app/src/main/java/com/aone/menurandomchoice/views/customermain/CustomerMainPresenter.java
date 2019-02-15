package com.aone.menurandomchoice.views.customermain;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.network.model.MenuLocationResponseBody;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.menuregister.adapter.MenuCategoryAdapterContract;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import android.util.Log;

import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.mapper.MenuMapper;

import java.util.List;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class CustomerMainPresenter extends BasePresenter<CustomerMainContract.View>
    implements CustomerMainContract.Presenter {

    private MenuCategoryAdapterContract.Model<MenuCategoryItem> menuCategoryAdapterModel;
    private List<MenuLocation> menuList;

    public void requestMenuList(double latitude, double longitude) {
        getRepository().requestMenuLocation(MenuMapper.createRequestLocationQueryMap(latitude,longitude),
                new NetworkResponseListener<List<MenuLocation>>() {
                    @Override
                    public void onReceived(@NonNull List<MenuLocation> response) {
                        Log.d("Menu", response.toString());
                        menuList = response;
                    }

                    @Override
                    public void onError() {

                    }
        });
    }

    public int getNumberFiltered(double centerLat, double centerLon, double radius, int category) {

        int count = 0;
        int len = menuList.size();
        double distance = 0;
        radius = radius*radius;
        for(int i = 0; i < len; i++) {
            if(menuList.get(i).getCategory() == category ) {
                distance = Math.pow(centerLat - menuList.get(i).getLatitude(), 2) + Math.pow(centerLon - menuList.get(i).getLongitude(), 2);
                if (radius >= distance) {
                    count++;
                }
            }
        }
        return count;
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
