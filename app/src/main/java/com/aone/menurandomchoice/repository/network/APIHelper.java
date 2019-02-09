package com.aone.menurandomchoice.repository.network;

import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;

import androidx.annotation.NonNull;
import retrofit2.Call;

public interface APIHelper {
    Call<AddressResponseBody> executeLocationSearch(@NonNull NetworkResponseListener<AddressResponseBody> networkResponseListener, String Query);
}
