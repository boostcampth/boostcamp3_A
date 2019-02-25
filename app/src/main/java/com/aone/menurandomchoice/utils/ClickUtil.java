package com.aone.menurandomchoice.utils;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;

public class ClickUtil {

    private static final int PREVENT_DUPLICATE_DELAY = 200;

    public static void preventDuplicateClick(@Nullable final View view) {
        if(view != null) {
            view.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                }
            }, PREVENT_DUPLICATE_DELAY);
        }
    }

    public static void preventDuplicateClick(@Nullable final MenuItem menuItem) {
        if(menuItem != null) {
            menuItem.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    menuItem.setEnabled(true);
                }
            }, PREVENT_DUPLICATE_DELAY);
        }
    }

}
