package com.aone.menurandomchoice.repository.local.pref;

import androidx.annotation.NonNull;

public interface PreferencesHelper {

    void saveRegisteredImageLocalPath(@NonNull String path);

    @NonNull
    String getSavedRegisterImageLoadPath();

}
