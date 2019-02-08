package com.aone.menurandomchoice.repository.server;

import android.content.Context;
import android.content.SharedPreferences;

import com.aone.menurandomchoice.GlobalApplication;

import java.util.Date;

import androidx.annotation.NonNull;

public class ServerDataRepository implements ServerDataHelper {

    private static ServerDataRepository serverDataRepository;

    public static ServerDataRepository getInstance() {
        if(serverDataRepository == null) {
            serverDataRepository = new ServerDataRepository();
        }

        return serverDataRepository;
    }


    /**
     * do check sign up of userId from server
     * but we don't have a server yet.
     * so, I used virtual logic.
     */
    @Override
    public void requestSignedUpCheck(long userId, @NonNull OnSignedUpCheckListener onSignedUpCheckListener) {
        try {
            SharedPreferences pref = GlobalApplication.getGlobalApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
            boolean isSignedUp = pref.getBoolean(String.valueOf(userId), false);
            if(isSignedUp) {
                onSignedUpCheckListener.onAlreadySignedUp();
            } else {
                onSignedUpCheckListener.onNotSignUp();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * do check sign up of userId from server
     * but we don't have a server yet.
     * so, I used virtual logic.
     */
    @Override
    public void requestSignUp(long userId, @NonNull String accessKey, @NonNull OnSignUpRequestListener onSignUpRequestListener) {
        try {
            SharedPreferences pref = GlobalApplication.getGlobalApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(String.valueOf(userId), true);
            editor.commit();

            onSignUpRequestListener.onSignUpSuccess();
        } catch (Exception e) {
            onSignUpRequestListener.onSignUpFail();
        }
    }

    @Override
    public void checkStoreUpdated(@NonNull String updateTime, @NonNull OnStoreUpdatedCheckListener onStoreUpdatedCheckListener) {
        //Todo. request isUpdated to Server
    }

    @Override
    public void requestStoreDetail(int storeIdx, @NonNull OnStoreDetailRequestListener onStoreDetailRequestListener) {
        //Todo. request StoreDetail to Server

    }


}
