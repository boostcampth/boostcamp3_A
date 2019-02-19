package com.aone.menurandomchoice.repository.remote;

import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.model.EmptyObject;
import com.aone.menurandomchoice.repository.model.OwnerInfo;
import com.aone.menurandomchoice.repository.model.SignUpData;
import com.aone.menurandomchoice.repository.remote.response.JMTResponseBody;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.model.KakaoAddressResult;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @GET("maps")
    Call<JMTResponseBody<List<MenuLocation>>> getMenuLocation(@QueryMap Map<String, String> location);

    @GET("https://dapi.kakao.com/v2/local/search/keyword.json")
    Call<KakaoAddressResult> getAddress(@Header("Authorization") String authorization,
                                        @Query("query") String query);

    @GET("stores/{storeIdx}")
    Call<JMTResponseBody<StoreDetail>> getStoreDetail(@Path("storeIdx") final int storeIdx);

    @GET("stores/{storeIdx}/updates")
    Call<JMTResponseBody<EmptyObject>> checkStoreUpdated(@Path("storeIdx") final int storeIdx,
                                                         @Query("updateTime") String updateTime);

    @POST("login")
    Call<JMTResponseBody<LoginData>> getSignedUpCheckRequest(@Body OwnerInfo ownerInfo);

    @POST("access")
    Call<JMTResponseBody<LoginData>> getSignUpRequest(@Body SignUpData signUpData);

    @GET("maps/menu")
    Call<JMTResponseBody<List<MenuDetail>>> createMenuListRequestCall(@QueryMap Map<String, String> searchRequest);

}
