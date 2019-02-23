package com.aone.menurandomchoice.utils;

import android.os.Handler;
import android.view.View;

public class ClickUtil {

    private static final int PREVENT_DUPLICATE_DELAY = 200;
    private static Handler handler = new Handler();

    public static void preventDuplicateClick(final View view) {
        view.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, PREVENT_DUPLICATE_DELAY);
    }

}
