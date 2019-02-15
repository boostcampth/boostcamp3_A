package com.aone.menurandomchoice.repository.remote.response;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class KakaoCallback<T> implements Callback<T> {

    private NetworkResponseListener<T> listener;

    public KakaoCallback(@NonNull NetworkResponseListener<T> listener) {
        this.listener = listener;
    }

    @EverythingIsNonNull
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful() && response.body() != null) {
            listener.onReceived(response.body());
        }
    }

    @EverythingIsNonNull
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(!call.isCanceled()) {
            listener.onError();
        }
    }
}
