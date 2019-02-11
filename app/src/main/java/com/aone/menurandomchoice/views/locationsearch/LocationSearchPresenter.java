package com.aone.menurandomchoice.views.locationsearch;

import android.util.Log;

import com.aone.menurandomchoice.repository.network.NetworkResponseListener;
import com.aone.menurandomchoice.repository.network.mapper.MenuMapper;
import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.aone.menurandomchoice.repository.network.model.MenuLocationResponseBody;
import com.aone.menurandomchoice.repository.network.pojo.KakaoAddressResult;
import com.aone.menurandomchoice.views.base.BasePresenter;
import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapterModel;

import java.util.List;

import androidx.annotation.NonNull;

public class LocationSearchPresenter extends BasePresenter<LocationSearchContract.View>
        implements LocationSearchContract.Presenter {

    private BaseRecyclerViewAdapterModel<KakaoAddressResult> adapterModel;

    public void setAdapter(@NonNull BaseRecyclerViewAdapterModel<KakaoAddressResult> adapterModel) {
        this.adapterModel = adapterModel;
    }

    private void updateList(List<KakaoAddressResult> documents) {
        this.adapterModel.setItems(documents);
    }

    public void requestMenuLocation(int position) {
        Log.d("Clicked ViewHolder", adapterModel.getItem(position).getX()+"");
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

    public void requestLocationSearch(@NonNull String Query) {
        getRepository().executeLocationSearch(Query, new NetworkResponseListener<AddressResponseBody>() {
                @Override
                public void onError() {

                }

                @Override
                public void onReceived(@NonNull AddressResponseBody response) {
                    updateList(response.getDocuments());
                }
            });
    }
}
