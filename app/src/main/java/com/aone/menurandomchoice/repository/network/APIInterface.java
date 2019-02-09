package com.aone.menurandomchoice.repository.network;

import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.aone.menurandomchoice.repository.network.pojo.MenuLocation;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {
    @GET("http://ec2-54-180-49-154.ap-northeast-2.compute.amazonaws.com:8080/maps")
    Call<List<MenuLocation>> getMenuLocation(@QueryMap Map<String, String> location);

    @GET("https://dapi.kakao.com/v2/local/search/address.json")
    Call<AddressResponseBody> getAddress(@Header("Authorization") String authorization, @Query("query") String query);
}
