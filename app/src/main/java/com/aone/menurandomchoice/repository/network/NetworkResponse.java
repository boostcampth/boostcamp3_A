package com.aone.menurandomchoice.repository.network;

import android.util.Log;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class NetworkResponse<T> implements Callback<T> {

    private NetworkResponseListener<T> listener;

    NetworkResponse(NetworkResponseListener<T> listener) {
        this.listener = listener;
    }

    @Override
    @EverythingIsNonNull
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if(response.isSuccessful()) {
            Log.d("onResponse", "response success");
            if (listener != null) {
                listener.onReceived(response.body());
            }
        } else {
            Log.d("onResponse", "response fail");
        }
    }

    @Override
    @EverythingIsNonNull
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (call.isCanceled()) {
            Log.e("onFailure", "request was cancelled");
            if (listener != null) {
                listener.onError();
            }

        } else {
            Log.e("onFailure", "other larger issue, i.e. no network connection?");
        }
    }
}
