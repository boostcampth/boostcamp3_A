package com.aone.menurandomchoice.utils;

import android.util.DisplayMetrics;

import com.aone.menurandomchoice.GlobalApplication;

public class DisplayUtil {

    public static float convertToDP(float pixel) {
        DisplayMetrics displayMetrics = GlobalApplication
                .getGlobalApplicationContext()
                .getResources()
                .getDisplayMetrics();

        return pixel / (displayMetrics.densityDpi / 160f);
    }

}
