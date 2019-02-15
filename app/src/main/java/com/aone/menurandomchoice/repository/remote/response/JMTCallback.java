package com.aone.menurandomchoice.repository.remote.response;

import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class JMTCallback<T> implements Callback<JMTResponseBody<T>> {

    private NetworkResponseListener<T> listener;

    public JMTCallback(@NonNull NetworkResponseListener<T> listener) {
        this.listener = listener;
    }

    @EverythingIsNonNull
    @Override
    public void onResponse(Call<JMTResponseBody<T>> call, Response<JMTResponseBody<T>> response) {
        if(response.isSuccessful()) {
            JMTResponseBody<T> JMTResponseBody = response.body();
            if(JMTResponseBody != null) {
                int statusCode = JMTResponseBody.getStatus();
                if(statusCode == 200) {
                    listener.onReceived(JMTResponseBody.getData());
                } else {
                    listener.onError();
                }
            } else {
                listener.onError();
            }
        } else {
            listener.onError();
        }
    }

    @EverythingIsNonNull
    @Override
    public void onFailure(Call<JMTResponseBody<T>> call, Throwable t) {
        if(!call.isCanceled()) {
            call.cancel();
        }

        listener.onError();
    }



}
