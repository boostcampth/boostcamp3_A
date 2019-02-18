package com.aone.menurandomchoice.repository.local.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.views.storeedit.StoreEditActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;

public class PreferencesRepository implements PreferencesHelper {

    private static final String PREF_FILE_NAME = "PREF_FILE_NAME";
    private static final String PREF_REGISTER_IMAGE_LOCAL_PATH = "PREF_REGISTER_IMAGE_LOCAL_PATH";
    private static final String PREF_REGISTER_MENU_DETAIL_INFO = "PREF_REGISTER_MENU_DETAIL_INFO";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public PreferencesRepository() {
        setUp();
    }

    private void setUp() {
        sharedPreferences = GlobalApplication
                .getGlobalApplicationContext()
                .getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        gson = new GsonBuilder().create();
    }

    @Override
    public void saveRegisterMenuInfo(@NonNull MenuDetail menuDetail) {
        int sequence = menuDetail.getSequence();
        String menuDetailJSON = gson.toJson(menuDetail, MenuDetail.class);
        sharedPreferences.edit().putString(PREF_REGISTER_MENU_DETAIL_INFO + sequence, menuDetailJSON).apply();
    }
}
