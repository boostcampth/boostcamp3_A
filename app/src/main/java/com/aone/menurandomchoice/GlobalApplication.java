package com.aone.menurandomchoice;

import android.app.Application;

import com.aone.menurandomchoice.repository.oauth.KakaoSDKAdapter;
import com.kakao.auth.KakaoSDK;

// FIXME 사용하는 라이브러리가 많아지면 나중에 multidex환경을 구성해야 하므로 MultiDexApplicatoin 사용을 고려해주세요
public class GlobalApplication extends Application {
    private static volatile GlobalApplication instance = null;

    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null) {
            throw new IllegalStateException("this application does not extends Application.class");
        }

        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        setUp();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        instance = null;
    }

    private void setUp() {
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }
}
