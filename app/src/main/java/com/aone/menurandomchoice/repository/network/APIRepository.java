package com.aone.menurandomchoice.repository.network;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.Repository;
import com.aone.menurandomchoice.repository.model.BaseResponse;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.network.model.AddressResponseBody;
import com.aone.menurandomchoice.repository.network.model.MenuLocationResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRepository implements APIHelper {

    private static APIRepository apiRepositoryInstance;
    private APIInterface apiInstance;
    private Call<AddressResponseBody> addressResponseBodyCall;
    private Call<MenuLocationResponseBody> menuLocationResponseBodyCall;
    private Call<BaseResponse> checkStoreUpdatedCall;
    private Call<BaseResponse<StoreDetail>> storeDetailResponseCall;

    public static final int STATUS_OK = 200;
    public static final int STATUS_NOT_MATCH = 204;

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

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
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

    @Override
    public void requestStoreDetail(@NonNull final StoreDetail cachedStoreDetail, final int storeIdx,
                                   @NonNull final Repository.OnLoadStoreDetailListener onLoadStoreDetailListener) {
        storeDetailResponseCall = apiInstance.getStoreDetail(storeIdx);
        storeDetailResponseCall.enqueue(new Callback<BaseResponse<StoreDetail>>() {
            @Override
            public void onFailure(@NonNull Call<BaseResponse<StoreDetail>> call, @NonNull Throwable t) {
                onLoadStoreDetailListener.onFailToLoadStoreDetail(cachedStoreDetail, t.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call<BaseResponse<StoreDetail>> call,
                                   @NonNull Response<BaseResponse<StoreDetail>> storeDetailResponse) {

                int storeDetailStatus = storeDetailResponse.body().getStatus();

                if(storeDetailStatus == STATUS_OK) {
                    onLoadStoreDetailListener.onStoreDetailLoaded(storeDetailResponse.body().getData());
                }
                else {
                    onLoadStoreDetailListener.onFailToLoadStoreDetail(cachedStoreDetail, "Server Internal Error");
                }
            }
        });

    }

    @Override
    public void checkStoreUpdated(@NonNull final StoreDetail cachedStoreDetail, final int storeIdx, @NonNull final String updateTime,
                                  @NonNull final Repository.OnLoadStoreDetailListener onLoadStoreDetailListener) {

        checkStoreUpdatedCall = apiInstance.checkStoreUpdated(storeIdx, updateTime);
        checkStoreUpdatedCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                onLoadStoreDetailListener.onFailToLoadStoreDetail(cachedStoreDetail, t.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> checkStoreResponse) {

                int checkStoreStatus = checkStoreResponse.body().getStatus();

                if (checkStoreStatus == STATUS_NOT_MATCH) {
                    requestStoreDetail(cachedStoreDetail, storeIdx, onLoadStoreDetailListener);
                } else if (checkStoreStatus == STATUS_OK) {
                    onLoadStoreDetailListener.onStoreDetailLoaded(cachedStoreDetail);
                } else {
                    onLoadStoreDetailListener.onFailToLoadStoreDetail(cachedStoreDetail, "Server Internal Error");
                }
            }
        });

    }

}
