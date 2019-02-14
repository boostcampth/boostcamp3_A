package com.aone.menurandomchoice.views.customermain;

import android.util.Log;

import com.aone.menurandomchoice.repository.network.NetworkResponseListener;
import com.aone.menurandomchoice.repository.network.mapper.MenuMapper;
import com.aone.menurandomchoice.repository.network.model.MenuLocationResponseBody;
import com.aone.menurandomchoice.repository.network.pojo.KakaoAddressResult;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapterModel;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;

import androidx.annotation.NonNull;

public class CustomerMainPresenter extends BasePresenter<CustomerMainContract.View>
    implements CustomerMainContract.Presenter {

    public void requestMenuList(double latitude,double longitude) {
        getRepository().requestMenuLocation(MenuMapper.createRequestLocationQueryMap(37.495573,127.039073),
                new NetworkResponseListener<MenuLocationResponseBody>() {
                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onReceived(MenuLocationResponseBody response) {
                        Log.d("Menu", response.getMessage());
                    }
                });
    }

    public void stopNetwork() {
        getRepository().cancelAll();
    }
}
