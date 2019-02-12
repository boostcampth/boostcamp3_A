package com.aone.menurandomchoice.utils;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideUtil {

    public static void loadImage(ImageView imageView, int resourceId) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .thumbnail(0.1f)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, Uri uri) {
        Glide.with(imageView.getContext())
                .load(uri)
                .thumbnail(0.1f)
                .into(imageView);
    }

}
