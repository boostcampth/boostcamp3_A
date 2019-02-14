package com.aone.menurandomchoice.repository.network;

import com.aone.menurandomchoice.repository.model.BaseResponse;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.aone.menurandomchoice.repository.network.model.MenuLocationResponseBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @GET("maps")
    Call<MenuLocationResponseBody> getMenuLocation(@QueryMap Map<String, String> location);

    @GET("https://dapi.kakao.com/v2/local/search/address.json")
    Call<AddressResponseBody> getAddress(@Header("Authorization") String authorization, @Query("query") String query);

    @GET("stores/{storeIdx}")
    Call<BaseResponse<StoreDetail>> getStoreDetail(@Path("storeIdx") final int storeIdx);

    @GET("stores/{storeIdx}/updates")
    Call<BaseResponse> checkStoreUpdated(@Path("storeIdx") final int storeIdx, @Query("updateTime") String updateTime);

}
