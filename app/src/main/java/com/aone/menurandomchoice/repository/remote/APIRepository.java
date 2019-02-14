package com.aone.menurandomchoice.repository.remote;

import android.content.Context;
import android.content.SharedPreferences;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.Repository;
import com.aone.menurandomchoice.repository.pojo.MenuLocation;
import com.aone.menurandomchoice.repository.remote.response.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.ResponseBody;
import com.aone.menurandomchoice.repository.pojo.StoreDetail;
import com.aone.menurandomchoice.repository.remote.response.AddressResponseBody;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIRepository implements APIHelper {

    private static final int STATUS_OK = 200;
    private static final int STATUS_NOT_MATCH = 204;

    private static APIRepository apiRepositoryInstance;
    private APICreator apiCreator;

    public static APIRepository getInstance() {
        if(apiRepositoryInstance == null) {
            apiRepositoryInstance = new APIRepository();
        }
        return apiRepositoryInstance;
    }

    private APIRepository() {
        setUp();
    }

    private void setUp() {
        apiCreator = new APICreator();
    }

    @Override
    public void executeLocationSearch(@NonNull String query, @NonNull NetworkResponseListener<AddressResponseBody> listener) {
        String REST_API_KEY = GlobalApplication.getGlobalApplicationContext().getString(R.string.KAKAO_REST_API_KEY);

        apiCreator.getApiInstance()
                .getAddress(REST_API_KEY, query)
                .enqueue(new NetworkCallback<>(listener));
    }

    @Override
    public void requestMenuLocation(@NonNull Map<String, String> queryMap,
                                    @NonNull NetworkResponseListener<ResponseBody<List<MenuLocation>>> listener) {
        apiCreator.getApiInstance()
                .getMenuLocation(queryMap)
                .enqueue(new NetworkCallback<>(listener));
    }


    public void requestStoreDetail(final int storeIdx,
                                   @NonNull final Repository.OnLoadStoreDetailListener onLoadStoreDetailListener) {
        apiCreator.getApiInstance()
                .getStoreDetail(storeIdx)
                .enqueue(new NetworkCallback<>(new NetworkResponseListener<ResponseBody<StoreDetail>>() {
                        @Override
                        public void onReceived(@NonNull ResponseBody<StoreDetail> response) {
                            if(response.getStatus() == STATUS_OK) {
                                onLoadStoreDetailListener.onStoreDetailLoaded(response.getData());
                            } else {
                                onLoadStoreDetailListener.onFailToLoadStoreDetail(response.getMessage());
                            }
                        }

                        @Override
                        public void onError() {
                            onLoadStoreDetailListener.onFailToLoadStoreDetail("서버에러");
                        }
                    })
                );
    }

    @Override
    public void checkStoreUpdated(final int storeIdx,
                                  @NonNull final String updateTime,
                                  @NonNull final OnCachedDataSameCheckListener onCachedDataSameCheckListener) {
        apiCreator.getApiInstance()
                .checkStoreUpdated(storeIdx, updateTime)
                .enqueue(new NetworkCallback<>(new NetworkResponseListener<ResponseBody>() {
                        @Override
                        public void onReceived(@NonNull ResponseBody response) {
                            if(response.getStatus() == STATUS_OK) {
                                onCachedDataSameCheckListener.onCachedDataIsSame();
                            } else if(response.getStatus() == STATUS_NOT_MATCH){
                                onCachedDataSameCheckListener.onCachedDataIsNotSame();
                            } else {
                                onCachedDataSameCheckListener.onFail(response.getMessage());
                            }
                        }

                        @Override
                        public void onError() {
                            onCachedDataSameCheckListener.onFail("서버에러");
                        }
                    })
                );
    }


    /**
     * do check sign up of userId from server
     * but we don't have a server yet.
     * so, I used virtual logic.
     */
    @Override
    public void requestSignedUpCheck(long userId, @NonNull OnSignedUpCheckListener onSignedUpCheckListener) {
        try {
            SharedPreferences pref = GlobalApplication.getGlobalApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
            boolean isSignedUp = pref.getBoolean(String.valueOf(userId), false);
            if(isSignedUp) {
                onSignedUpCheckListener.onAlreadySignedUp();
            } else {
                onSignedUpCheckListener.onNotSignUp();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * do check sign up of userId from server
     * but we don't have a server yet.
     * so, I used virtual logic.
     */
    @Override
    public void requestSignUp(long userId, @NonNull String accessKey, @NonNull OnSignUpRequestListener onSignUpRequestListener) {
        try {
            SharedPreferences pref = GlobalApplication.getGlobalApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(String.valueOf(userId), true);
            editor.commit();

            onSignUpRequestListener.onSignUpSuccess();
        } catch (Exception e) {
            onSignUpRequestListener.onSignUpFail();
        }
    }


    class NetworkCallback<T> implements Callback<T> {

        private NetworkResponseListener<T> listener;

        NetworkCallback(NetworkResponseListener<T> listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
            if(listener != null) {
                if(response.isSuccessful() && response.body() != null) {
                    listener.onReceived(response.body());
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
            if(!call.isCanceled()) {
                if(listener != null) {
                    listener.onError();
                }
            }
        }
    }

}
