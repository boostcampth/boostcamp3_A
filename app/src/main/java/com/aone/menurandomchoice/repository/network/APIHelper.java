package com.aone.menurandomchoice.repository.network;

import com.aone.menurandomchoice.repository.model.BaseResponse;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.aone.menurandomchoice.repository.network.model.MenuLocationResponseBody;
import com.aone.menurandomchoice.repository.network.pojo.MenuLocation;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Call;

public interface APIHelper {
    void executeLocationSearch(
            @NonNull String Query,
            @NonNull NetworkResponseListener<AddressResponseBody> networkResponseListener);

    void requestMenuLocation(
            @NonNull Map<String, String> queryMap,
            @NonNull NetworkResponseListener<MenuLocationResponseBody> networkResponseListener);

    void requestStoreDetail(
            @NonNull int storeIdx,
            @NonNull NetworkResponseListener<BaseResponse<StoreDetail>> networkResponseListener);

}
