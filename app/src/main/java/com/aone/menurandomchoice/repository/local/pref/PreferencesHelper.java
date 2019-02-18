package com.aone.menurandomchoice.repository.local.pref;

import com.aone.menurandomchoice.repository.model.MenuDetail;

import androidx.annotation.NonNull;

public interface PreferencesHelper {

    void saveRegisterMenuInfo(@NonNull MenuDetail menuDetail);

}
