package com.aone.menurandomchoice.views.customermain;

import android.util.Log;

import com.aone.menurandomchoice.repository.pojo.MenuLocation;
import com.aone.menurandomchoice.repository.remote.response.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.mapper.MenuMapper;
import com.aone.menurandomchoice.repository.remote.response.ResponseBody;
import com.aone.menurandomchoice.views.base.BasePresenter;

import java.util.List;

import androidx.annotation.NonNull;

public class CustomerMainPresenter extends BasePresenter<CustomerMainContract.View>
    implements CustomerMainContract.Presenter {

    public void requestMenuList(double latitude,double longitude) {
        getRepository().requestMenuLocation(MenuMapper.createRequestLocationQueryMap(37.495573,127.039073),
                new NetworkResponseListener<ResponseBody<List<MenuLocation>>>() {

                    @Override
                    public void onReceived(@NonNull ResponseBody<List<MenuLocation>> response) {
                        Log.d("Menu", response.getMessage());
                    }

                    @Override
                    public void onError() {

                    }

                });
    }

    public void stopNetwork() {
        getRepository().cancelAll();
    }
}
