package com.aone.menurandomchoice.utils;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtil {

    public static void loadImage(ImageView imageView, int resourceId) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .thumbnail(0.1f)
                .into(imageView);
    }

    public static void loadImageWithSkipCache(ImageView imageView, String uri) {
        Glide.with(imageView.getContext())
                .load(uri)
                .apply(new RequestOptions()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .transform(new RoundedCorners(30)))
                .thumbnail(0.1f)
                .into(imageView);
    }

}
