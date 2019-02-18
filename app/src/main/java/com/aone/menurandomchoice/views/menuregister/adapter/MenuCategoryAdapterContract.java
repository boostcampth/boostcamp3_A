package com.aone.menurandomchoice.views.menuregister.adapter;

import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapterModel;
import com.aone.menurandomchoice.views.menuregister.adapter.viewholder.OnMenuCategoryClickListener;

import androidx.annotation.NonNull;

public interface MenuCategoryAdapterContract {

    interface View {

        void setOnMenuCategoryClickListener(@NonNull OnMenuCategoryClickListener onMenuCategoryClickListener);

    }

    interface Model<T> extends BaseRecyclerViewAdapterModel<T> {

        void selectDefaultCategory();

        void setSelectedPositionOfMenuCategories(int position);

        void setCategory(@NonNull String category);

        @NonNull
        String getSelectedCategory();

    }

}
