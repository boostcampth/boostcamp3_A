package com.aone.menurandomchoice.repository.remote;

import android.util.Log;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.UpdateTime;
import com.aone.menurandomchoice.repository.model.KakaoAddress;
import com.aone.menurandomchoice.repository.model.KakaoAddressResult;
import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.OwnerInfo;
import com.aone.menurandomchoice.repository.model.SignUpData;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.remote.mapper.MenuMapper;
import com.aone.menurandomchoice.repository.remote.response.JMTCallback;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.repository.remote.response.KakaoCallback;
import com.aone.menurandomchoice.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class APIRepository implements APIHelper {

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
    public void executeLocationSearch(@NonNull String query,
                                      @NonNull NetworkResponseListener<KakaoAddressResult> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            String REST_API_KEY = GlobalApplication.getGlobalApplicationContext().getString(R.string.KAKAO_REST_API_KEY);
            apiCreator.getApiInstance()
                    .getAddress(REST_API_KEY, query)
                    .enqueue(new KakaoCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    @EverythingIsNonNull
    public void requestMenuLocation(@NonNull Map<String, String> queryMap,
                                    @NonNull final NetworkResponseListener<List<MenuLocation>> listener) {
        // test with mock data
        Map<String, String> queryMapFD6 = new HashMap<>();
        queryMapFD6.put("category_group_code", "FD6");
        queryMapFD6.put("x", queryMap.get("longitude"));
        queryMapFD6.put("y", queryMap.get("latitude"));
        queryMapFD6.put("radius", "1000");
        queryMapFD6.put("sort","distance");

        final String category[] = {"한식", "일식", "중식", "양식"};
        final List<MenuLocation> mockData = new ArrayList<>();
        apiCreator
                .getApiInstance()
                .getMenuFD6(GlobalApplication.getGlobalApplicationContext().getString(R.string.KAKAO_REST_API_KEY), queryMapFD6)
                .enqueue(new Callback<KakaoAddressResult>() {
                    @Override
                    public void onResponse(Call<KakaoAddressResult> call, Response<KakaoAddressResult> response) {
                        if(response.isSuccessful()) {
                                List<KakaoAddress> mock = response.body().getDocuments();
                                int len = mock.size();
                                for (int i = 0; i < len; i++) {
                                    mockData.add(new MenuLocation(mock.get(i).getY(), mock.get(i).getX(), category[i%4]));
                                }
                                listener.onReceived(mockData);
                        } else {
                            Log.d("Mock", "response success but fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<KakaoAddressResult> call, Throwable t) {
                        if(call.isCanceled()) {

                        } else {

                        }
                    }
                });

/*        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getMenuLocation(queryMap)
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }*/
    }

    @Override
    public void requestStoreDetail(int storeIdx,
                                   @NonNull NetworkResponseListener<StoreDetail> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getStoreDetail(storeIdx)
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }

    }

    @Override
    public void checkStoreUpdated(int storeIdx,
                                  @NonNull NetworkResponseListener<UpdateTime> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .checkStoreUpdated(storeIdx)
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestSignedUpCheck(long userId,
                                     @NonNull NetworkResponseListener<LoginData> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getSignedUpCheckRequest(new OwnerInfo(String.valueOf(userId)))
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestSignUp(long userId,
                              @NonNull String accessKey,
                              @NonNull NetworkResponseListener<LoginData> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getSignUpRequest(new SignUpData(String.valueOf(userId), accessKey))
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestMenuList(@NonNull MenuSearchRequest menuSearchRequest,
                                                 @NonNull NetworkResponseListener<List<MenuDetail>> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .createMenuListRequestCall(MenuMapper.createMenuListSearchQueryMap(menuSearchRequest))
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

}
