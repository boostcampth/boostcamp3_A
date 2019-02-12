package com.aone.menurandomchoice.repository.network;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.aone.menurandomchoice.repository.network.model.MenuLocationResponseBody;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRepository implements APIHelper {

    private static APIRepository apiRepositoryInstance;
    private APIInterface apiInstance;
    private Call<AddressResponseBody> addressResponseBodyCall;
    private Call<MenuLocationResponseBody> menuLocationResponseBodyCall;

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
    public void executeLocationSearch(@NonNull String Qeury, @NonNull NetworkResponseListener<AddressResponseBody> listener) {
        String REST_API_KEY = GlobalApplication.getGlobalApplicationContext().getString(R.string.KAKAO_REST_API_KEY);
        addressResponseBodyCall = apiInstance.getAddress(REST_API_KEY, Qeury);
        addressResponseBodyCall.enqueue(new NetworkResponse<>(listener));
    }

    @Override
    public void requestMenuLocation(@NonNull Map<String, String> queryMap, @NonNull NetworkResponseListener<MenuLocationResponseBody> listener) {
        menuLocationResponseBodyCall = apiInstance.getMenuLocation(queryMap);
        menuLocationResponseBodyCall.enqueue( new NetworkResponse<>(listener));
    }

}