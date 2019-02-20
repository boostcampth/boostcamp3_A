package com.aone.menurandomchoice.repository.remote;

import com.aone.menurandomchoice.repository.model.EmptyObject;
import com.aone.menurandomchoice.repository.model.UpdateTime;
import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.model.KakaoAddressResult;
import com.aone.menurandomchoice.repository.model.MenuLocation;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public interface APIHelper {

    void executeLocationSearch(@NonNull String query,
                               @NonNull NetworkResponseListener<KakaoAddressResult> networkResponseListener);

    void requestMenuLocation(@NonNull Map<String, String> queryMap,
                             @NonNull NetworkResponseListener<List<MenuLocation>> networkResponseListener);

    void requestStoreDetail(int storeIdx,
                            @NonNull NetworkResponseListener<StoreDetail> networkResponseListener);

    void checkStoreUpdated(int storeIdx,
                           @NonNull NetworkResponseListener<UpdateTime> networkResponseListener);

    void requestSignedUpCheck(long userId,
                              @NonNull NetworkResponseListener<LoginData> networkResponseListener);

    void requestSignUp(long userId,
                       @NonNull String accessKey,
                       @NonNull NetworkResponseListener<LoginData> networkResponseListener);

    void requestMenuList(@NonNull MenuSearchRequest menuSearchRequest,
                         @NonNull NetworkResponseListener<List<MenuDetail>> networkResponseListener);

    void requestSaveStoreDetail(@NonNull StoreDetail storeDetail,
                                @NonNull NetworkResponseListener<EmptyObject> networkResponseListener);

}
