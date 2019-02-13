package com.aone.menurandomchoice.repository.local.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.aone.menurandomchoice.GlobalApplication;

import androidx.annotation.NonNull;

public class PreferencesRepository implements PreferencesHelper {

    private static final String PREF_FILE_NAME = "PREF_FILE_NAME";
    private static final String PREF_REGISTER_IMAGE_LOCAL_PATH = "PREF_REGISTER_IMAGE_LOCAL_PATH";

    private SharedPreferences sharedPreferences;

    public PreferencesRepository() {
        setUp();
    }

    private void setUp() {
        sharedPreferences = GlobalApplication
                .getGlobalApplicationContext()
                .getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveRegisteredImageLocalPath(@NonNull String path) {
        sharedPreferences.edit().putString(PREF_REGISTER_IMAGE_LOCAL_PATH, path).apply();
    }

    @NonNull
    @Override
    public String getSavedRegisterImageLoadPath() {
        return sharedPreferences.getString(PREF_REGISTER_IMAGE_LOCAL_PATH, "");
    }

}
