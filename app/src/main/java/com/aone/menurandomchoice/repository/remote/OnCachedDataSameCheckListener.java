package com.aone.menurandomchoice.repository.remote;

import androidx.annotation.NonNull;

public interface OnCachedDataSameCheckListener {

    void onCachedDataIsSame();

    void onCachedDataIsNotSame();

    void onFail(@NonNull String errorMessage);

}
