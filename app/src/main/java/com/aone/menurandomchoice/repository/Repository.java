package com.aone.menurandomchoice.repository;

import com.aone.menurandomchoice.repository.oauth.KakaoLoginHelper;
import com.aone.menurandomchoice.repository.server.ServerDataHelper;

public interface Repository extends KakaoLoginHelper, ServerDataHelper {

    void cancelAll();

}