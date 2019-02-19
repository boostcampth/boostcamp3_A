package com.aone.menurandomchoice.repository.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICreator {

    private static final String BASE_URL = "http://172.20.10.2:8080/";
//    private static final String BASE_URL = "http://ec2-54-180-49-154.ap-northeast-2.compute.amazonaws.com:8080/";
    private APIInterface apiInstance;

    public APIInterface getApiInstance() {
        if(apiInstance == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInstance = retrofit.create(APIInterface.class);
        }

        return apiInstance;
    }

}
