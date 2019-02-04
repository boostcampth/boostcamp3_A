package com.aone.menurandomchoice.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class BindingUtil {
    @BindingAdapter({"bind:photoUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        //ImageUtil.loadImage(imageView, url, errorDrawable);
    }


}
