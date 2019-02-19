package com.aone.menurandomchoice.repository.remote.mapper;

import com.aone.menurandomchoice.repository.model.MenuSearchRequest;

import java.util.HashMap;
import java.util.Map;

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

        if(menuSearchRequest.getRadius() != 0) {
            queryMap.put(QUERY_RADIUS, menuSearchRequest.getRadius() + "");
        }

        if(menuSearchRequest.getCategory() != null && menuSearchRequest.getCategory().length() != 0) {
            queryMap.put(QUERY_CATEGORY, menuSearchRequest.getCategory());
        }

        return queryMap;
    }

}
