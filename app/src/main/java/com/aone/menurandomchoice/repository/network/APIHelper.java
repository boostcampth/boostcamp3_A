package com.aone.menurandomchoice.repository.network;

import com.aone.menurandomchoice.repository.Repository;
import com.aone.menurandomchoice.repository.model.BaseResponse;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.aone.menurandomchoice.repository.network.model.MenuLocationResponseBody;

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

    void requestStoreDetail(@NonNull final StoreDetail cachedStoreDetail, final int storeIdx,
                            @NonNull final Repository.OnLoadStoreDetailListener onLoadStoreDetailListener);

    void checkStoreUpdated(
            @NonNull final StoreDetail cachedStoreDetail, final int storeIdx, @NonNull final String updateTime,
            @NonNull final Repository.OnLoadStoreDetailListener onLoadStoreDetailListener);

}
