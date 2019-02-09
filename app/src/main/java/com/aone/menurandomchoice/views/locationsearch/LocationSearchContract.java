package com.aone.menurandomchoice.views.locationsearch;

import com.aone.menurandomchoice.views.base.BaseContract;
import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapter;

import androidx.annotation.NonNull;

public interface LocationSearchContract {

    interface View extends BaseContract.View {
        void requestLocationSearchWord();
    }

    interface Presenter extends BaseContract.Presenter<LocationSearchContract.View> {

        void setAdapter(LocationSearchAdapter adapter);

        void requestLocationSearch(@NonNull String Query);
    }
}
