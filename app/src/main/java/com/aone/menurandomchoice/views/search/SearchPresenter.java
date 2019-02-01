package com.aone.menurandomchoice.views.search;

import com.aone.menurandomchoice.data.network.NetworkResponse;
import com.aone.menurandomchoice.data.network.NetworkResponseListener;
import com.aone.menurandomchoice.data.network.RetrofitAPI;
import com.aone.menurandomchoice.data.network.model.AddressResponseBody;

import androidx.annotation.NonNull;
import retrofit2.Call;

public class SearchPresenter implements NetworkResponseListener<AddressResponseBody>{

    private Call<AddressResponseBody> call;
    private NetworkResponse<AddressResponseBody> networkResponse;

    public SearchPresenter(NetworkResponse<AddressResponseBody> networkResponse) {
          this.networkResponse = networkResponse;
          this.networkResponse.setNetworkResponseListener(this);
    }

    @Override
    public void onError() {
    }

    @Override
    public void onResponseReceived(@NonNull AddressResponseBody response) {
    }

    public void getAddress(String Athorization, String Query) {
        this.call = RetrofitAPI.getAPI().getAddress(Athorization, Query);
        this.call.enqueue(networkResponse);
    }
}
