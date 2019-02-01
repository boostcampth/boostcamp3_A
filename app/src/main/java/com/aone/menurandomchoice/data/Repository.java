package com.aone.menurandomchoice.data;

import com.aone.menurandomchoice.data.oauth.KakaoLoginHelper;

public interface Repository extends KakaoLoginHelper {

    void cancelAll();

}
