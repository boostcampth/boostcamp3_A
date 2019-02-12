package com.aone.menurandomchoice.views.menuregister.helper;

import android.net.Uri;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import androidx.annotation.NonNull;

public class UCropCreateHelper {

    public UCrop createUCop(@NonNull Uri uri,
                            @NonNull String fileName,
                            float widthRatio,
                            float heightRatio,
                            int resizeWidth,
                            int resizeHeight) {

        UCrop uCrop = UCrop.of(uri, createDestinationUri(fileName))
                .withAspectRatio(widthRatio, heightRatio)
                .withMaxResultSize(resizeWidth, resizeHeight);

        uCrop.withOptions(createUCropOptions());

        return uCrop;
    }

    private Uri createDestinationUri(String fileName) {
        File file = new File(GlobalApplication.getGlobalApplicationContext().getCacheDir(), fileName);
        return Uri.fromFile(file);
    }

    private UCrop.Options createUCropOptions() {
        UCrop.Options options = new UCrop.Options();

        options.setStatusBarColor(GlobalApplication
                .getGlobalApplicationContext()
                .getResources()
                .getColor(R.color.black));

        options.setToolbarColor(GlobalApplication
                .getGlobalApplicationContext()
                .getResources()
                .getColor(R.color.white));

        options.setActiveWidgetColor(GlobalApplication
                .getGlobalApplicationContext()
                .getResources()
                .getColor(R.color.reddish));

        options.setToolbarWidgetColor(GlobalApplication
                .getGlobalApplicationContext()
                .getResources()
                .getColor(R.color.black));

        options.setToolbarTitle(GlobalApplication
                .getGlobalApplicationContext()
                .getResources()
                .getString(R.string.uCrop_toolbar_title));

        return options;
    }

}
