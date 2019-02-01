package com.aone.menurandomchoice.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.aone.menurandomchoice.GlobalApplication;

public class NetworkUtil {

    public static  boolean isNetworkConnecting() {
        Context context = GlobalApplication.getGlobalApplicationContext();

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            return networkInfo != null && networkInfo.isConnected();
        }

        return false;
    }

}
