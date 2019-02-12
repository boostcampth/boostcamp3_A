package com.aone.menurandomchoice.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;


import androidx.databinding.BindingAdapter;

public class BindingUtil {
    @BindingAdapter({"bind:photoUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(errorDrawable).error(errorDrawable))
                .into(imageView);
    }


}
