package com.aone.menurandomchoice.data.network;

import com.aone.menurandomchoice.data.network.model.AddressResponseBody;
import com.aone.menurandomchoice.data.network.pojo.MenuLocation;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class RetrofitAPI {

    private static APIInterface api = null;
    private static String BASE_URL = "http://ec2-54-180-49-154.ap-northeast-2.compute.amazonaws.com:8080/";

    public static APIInterface getAPI() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            api = retrofit.create(APIInterface.class);
        }
        return api;
    }

    public interface APIInterface {
        @GET("http://ec2-54-180-49-154.ap-northeast-2.compute.amazonaws.com:8080/maps")
        Call<List<MenuLocation>> getMenuLocation(@QueryMap Map<String, String> location);

      /*  @GET("http://ec2-54-180-49-154.ap-northeast-2.compute.amazonaws.com:8080/store/menu")
        Call<MenuDetailResponse> getMenuDetail(@QueryMap Map<String, String> radius);

        @GET("http://ec2-54-180-49-154.ap-northeast-2.compute.amazonaws.com:8080/maps/stores/{storeIdx}")
        Call<StoreDetailResponse> getStoreDetail(@Path("storeIdx") String storeIdx);*/

        @GET("https://dapi.kakao.com/v2/local/search/address.json")
        Call<AddressResponseBody> getAddress(@Header("Authorization") String authorization, @Query("query") String q);
    }

}
