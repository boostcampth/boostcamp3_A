package com.aone.menurandomchoice.repository.remote;

import com.aone.menurandomchoice.repository.pojo.MenuLocation;
import com.aone.menurandomchoice.repository.remote.response.ResponseBody;
import com.aone.menurandomchoice.repository.pojo.StoreDetail;
import com.aone.menurandomchoice.repository.remote.response.AddressResponseBody;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @GET("maps")
    Call<ResponseBody<List<MenuLocation>>> getMenuLocation(@QueryMap Map<String, String> location);

    @GET("https://dapi.kakao.com/v2/local/search/address.json")
    Call<AddressResponseBody> getAddress(@Header("Authorization") String authorization, @Query("query") String query);

    @GET("stores/{storeIdx}")
    Call<ResponseBody<StoreDetail>> getStoreDetail(@Path("storeIdx") final int storeIdx);

    @GET("stores/{storeIdx}/updates")
    Call<ResponseBody> checkStoreUpdated(@Path("storeIdx") final int storeIdx, @Query("updateTime") String updateTime);

}
