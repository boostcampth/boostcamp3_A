package com.aone.menurandomchoice.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideUtil {

    public static void loadImage(ImageView imageView, int resourceId) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .thumbnail(0.1f)
                .into(imageView);
    }

}
