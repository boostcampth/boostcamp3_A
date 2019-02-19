package com.aone.menurandomchoice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class DateUtil {

    @NonNull
    public static String getNowDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        return simpleDateFormat.format(date);
    }

}
