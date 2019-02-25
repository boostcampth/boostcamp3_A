package com.aone.menurandomchoice.utils;

import com.aone.menurandomchoice.GlobalApplication;

public class StringUtil {

    public static String getString(int resourceId) {
        return GlobalApplication
                .getGlobalApplicationContext()
                .getString(resourceId);
    }

}
