package com.aone.menurandomchoice.views.menuselect.adapter;

import com.aone.menurandomchoice.repository.model.MenuDetail;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface MenuSelectOverlapViewAdapterContract {

    interface View {

    }

    interface Model {

        void setItemList(@NonNull List<MenuDetail> itemList);

        @Nullable
        MenuDetail getTopViewData();

    }

}
