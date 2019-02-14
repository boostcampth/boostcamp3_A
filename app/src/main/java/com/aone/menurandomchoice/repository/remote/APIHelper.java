package com.aone.menurandomchoice.repository.remote;

import com.aone.menurandomchoice.repository.Repository;
import com.aone.menurandomchoice.repository.remote.response.AddressResponseBody;
import com.aone.menurandomchoice.repository.pojo.MenuLocation;
import com.aone.menurandomchoice.repository.remote.response.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.ResponseBody;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public interface APIHelper {

    void executeLocationSearch(@NonNull String query,
                               @NonNull NetworkResponseListener<AddressResponseBody> networkResponseListener);

    void requestMenuLocation( @NonNull Map<String, String> queryMap,
                              @NonNull NetworkResponseListener<ResponseBody<List<MenuLocation>>> networkResponseListener);

    void requestStoreDetail(int storeIdx,
                            @NonNull Repository.OnLoadStoreDetailListener onLoadStoreDetailListener);

    void checkStoreUpdated(int storeIdx,
                           @NonNull String updateTime,
                           @NonNull OnCachedDataSameCheckListener onCachedDataSameCheckListener);

    void requestSignedUpCheck(long userId, @NonNull OnSignedUpCheckListener onSignedUpCheckListener);

    void requestSignUp(long userId, @NonNull String accessKey, @NonNull OnSignUpRequestListener onSignUpRequestListener);

}
