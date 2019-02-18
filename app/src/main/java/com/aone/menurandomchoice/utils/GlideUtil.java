package com.aone.menurandomchoice.utils;

import android.widget.ImageView;

import com.aone.menurandomchoice.GlobalApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtil {

    public static void loadImage(ImageView imageView, int resourceId) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .into(imageView);
    }

    public static void loadImageWithSkipCache(ImageView imageView, String uri) {
        Glide.with(GlobalApplication.getGlobalApplicationContext())
                .load(uri)
                .apply(new RequestOptions()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transforms(new CenterCrop(), new RoundedCorners(30)))
                .thumbnail(0.1f)
                .into(imageView);
    }

    public static void loadImageWithSkipCache(ImageView imageView, int resourceId) {
        Glide.with(GlobalApplication.getGlobalApplicationContext())
                .load(resourceId)
                .apply(new RequestOptions()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transforms(new CenterCrop(), new RoundedCorners(30)))
                .thumbnail(0.1f)
                .into(imageView);
    }

}
