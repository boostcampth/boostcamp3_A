package com.aone.menurandomchoice.views.locationsearch;

import android.os.Bundle;
import android.os.Parcelable;

import com.aone.menurandomchoice.repository.network.NetworkResponseListener;
import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
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

    public void requestLocationSearch(@NonNull String Query) {
        getRepository().executeLocationSearch(Query, new NetworkResponseListener<AddressResponseBody>() {
                @Override
                public void onError() {

                }

                @Override
                public void onReceived(@NonNull AddressResponseBody response) {
                    if( isAttachView() ) {
                        updateList(response.getDocuments());
                    }
                }
        });
    }

    @Override
    public Parcelable getMenuData(int position) {
        Bundle posXY = new Bundle();
        posXY.putDouble("longitude", adapterModel.getItem(position).getX());
        posXY.putDouble("latitude", adapterModel.getItem(position).getY());
        return posXY;
    }

    @Override
    public void stopNetwork() {
        getRepository().cancelAll();
    }
}
