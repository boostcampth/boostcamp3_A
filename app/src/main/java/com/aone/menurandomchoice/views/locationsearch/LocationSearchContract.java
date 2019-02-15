package com.aone.menurandomchoice.views.locationsearch;

import android.os.Parcelable;

import com.aone.menurandomchoice.repository.model.KakaoAddress;
import com.aone.menurandomchoice.views.base.BaseContract;
import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapterModel;

import androidx.annotation.NonNull;

public interface LocationSearchContract {

    interface View extends BaseContract.View {

        void requestLocationSearchWord();

    }

    interface Presenter extends BaseContract.Presenter<LocationSearchContract.View> {

        void setAdapter(@NonNull BaseRecyclerViewAdapterModel<KakaoAddress> adapter);

        void requestLocationSearch(@NonNull String Query);

        Parcelable getMenuData(int position);

        void stopNetwork();

    }
}
