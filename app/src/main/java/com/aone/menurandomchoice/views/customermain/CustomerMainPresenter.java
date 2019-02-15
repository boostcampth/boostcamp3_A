package com.aone.menurandomchoice.views.customermain;

import android.util.Log;

import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.mapper.MenuMapper;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.views.base.BasePresenter;

import java.util.List;

import androidx.annotation.NonNull;

public class CustomerMainPresenter extends BasePresenter<CustomerMainContract.View>
    implements CustomerMainContract.Presenter {

    public void requestMenuList(double latitude,double longitude) {
        getRepository().requestMenuLocation(MenuMapper.createRequestLocationQueryMap(37.495573,127.039073),
                new NetworkResponseListener<List<MenuLocation>>() {
                    @Override
                    public void onReceived(@NonNull List<MenuLocation> response) {
                        Log.d("Menu", response.toString());
                    }

                    @Override
                    public void onError(JMTErrorCode errorCode) {

                    }
                });
    }

    public void stopNetwork() {
        getRepository().cancelAll();
    }
}
