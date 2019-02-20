package com.aone.menurandomchoice.repository.remote.mapper;

import android.text.TextUtils;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.StoreDetail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MenuMapper {

    private static final String QUERY_STRING_LATITUDE = "latitude";
    private static final String QUERY_STRING_LONGITUDE = "longitude";

    private static final String QUERY_RADIUS = "radius";
    private static final String QUERY_CATEGORY = "category";

    private static final String TAG_IMAGE_REGISTER_REQUEST = "photo";

    public static Map<String, String> createRequestLocationQueryMap(double latitude, double longitude){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(QUERY_STRING_LATITUDE, latitude+"");
        queryMap.put(QUERY_STRING_LONGITUDE, longitude+"");

        return queryMap;
    }

    public static Map<String, String> createMenuListSearchQueryMap(MenuSearchRequest menuSearchRequest){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(QUERY_STRING_LATITUDE, menuSearchRequest.getLatitude() + "");
        queryMap.put(QUERY_STRING_LONGITUDE, menuSearchRequest.getLongitude() + "");

        int radius = menuSearchRequest.getRadius();
        if(radius != 0) {
            queryMap.put(QUERY_RADIUS, radius + "");
        }

        String category = menuSearchRequest.getCategory();
        if(!TextUtils.isEmpty(category)) {
            queryMap.put(QUERY_CATEGORY, category);
        }

        return queryMap;
    }

    public static List<MultipartBody.Part> createRegisteredImageList(@NonNull StoreDetail storeDetail) {
        List<MultipartBody.Part> images = new ArrayList<>();
        List<MenuDetail> menuDetailList = storeDetail.getMenuList();
        for(int i=0; i<menuDetailList.size(); i++) {
            String photoUrl = menuDetailList.get(i).getPhotoUrl();
            if(!TextUtils.isEmpty(photoUrl)) {
                if (!photoUrl.contains("http")) {
                    File file = new File(photoUrl);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    images.add(MultipartBody.Part.createFormData(TAG_IMAGE_REGISTER_REQUEST, file.getName(), requestFile));
                }
            }
        }

        return images;
    }

}
