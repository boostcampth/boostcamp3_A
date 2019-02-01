package com.aone.menurandomchoice.repository.server;

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
    public void requestSignUpCheck(long userId, @NonNull OnSignedUpCheckListener onSignedUpCheckListener) {
        try {
            Thread.sleep(1000);
            onSignedUpCheckListener.onNotSignUp();
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
    public void requestSignUp(long userId, String accessKey, OnSignUpRequestListener onSignUpRequestListener) {
        try {
            Thread.sleep(1000);
            onSignUpRequestListener.onSignUpSuccess();
        } catch (Exception e) {

        }
    }
}
