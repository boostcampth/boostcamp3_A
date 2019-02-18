package com.aone.menurandomchoice.repository.remote.mapper;

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
        queryMap.put(QUERY_RADIUS, menuSearchRequest.getRadius() + "");
        queryMap.put(QUERY_CATEGORY, menuSearchRequest.getCategory());

        return queryMap;
    }

    public static List<MultipartBody.Part> createRegisteredImageList(@NonNull StoreDetail storeDetail) {
        List<MultipartBody.Part> images = new ArrayList<>();
        List<MenuDetail> menuDetailList = storeDetail.getMenuList();
        for(int i=0; i<menuDetailList.size(); i++) {
            String photoUrl = menuDetailList.get(i).getPhotoUrl();
            if(!photoUrl.contains("http")) {
                File file = new File(menuDetailList.get(i).getPhotoUrl());
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                images.add(MultipartBody.Part.createFormData("photo", file.getName(), requestFile));
            }
        }

        return images;
    }

}
