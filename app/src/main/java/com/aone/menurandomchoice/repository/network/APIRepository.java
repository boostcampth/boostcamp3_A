package com.aone.menurandomchoice.repository.network;

import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRepository implements APIHelper {

    private static APIRepository apiRepositoryInstance;
    private APIInterface apiInstance;

    public static APIRepository getInstance() {
        if(apiRepositoryInstance == null) {
            apiRepositoryInstance = new APIRepository();
        }
        return apiRepositoryInstance;
    }

    private APIRepository() {
            String BASE_URL = "http://ec2-54-180-49-154.ap-northeast-2.compute.amazonaws.com:8080/";

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

            apiInstance = retrofit.create(APIInterface.class);
    }

    @Override
    public Call<AddressResponseBody> executeLocationSearch(NetworkResponseListener<AddressResponseBody> listener, String Qeury) {
            Call<AddressResponseBody> call = apiInstance.getAddress("KakaoAK d6960a6792d64d6af209195c8bfa4094",Qeury);
            call.enqueue( new NetworkResponse<>(listener));
            return call;
    }
}
