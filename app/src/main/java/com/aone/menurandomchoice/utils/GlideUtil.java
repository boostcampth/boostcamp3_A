package com.aone.menurandomchoice.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.Nullable;

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

    public static void loadImageWithSkipCache(final ImageView imageView, String uri, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        Glide.with(GlobalApplication.getGlobalApplicationContext())
                .load(uri)
                .apply(new RequestOptions()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.error_image)
                        .transforms(new CenterCrop(), new RoundedCorners(30)))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .thumbnail(0.1f)
                .into(imageView);
    }

}
