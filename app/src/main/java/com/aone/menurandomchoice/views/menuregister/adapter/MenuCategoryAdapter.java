package com.aone.menurandomchoice.views.menuregister.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapter;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import com.aone.menurandomchoice.views.menuregister.adapter.viewholder.MenuCategoryViewHolder;
import com.aone.menurandomchoice.views.menuregister.adapter.viewholder.OnMenuCategoryClickListener;

import androidx.annotation.NonNull;

public class MenuCategoryAdapter extends BaseRecyclerViewAdapter<MenuCategoryItem, MenuCategoryViewHolder>
    implements MenuCategoryAdapterContract.View, MenuCategoryAdapterContract.Model<MenuCategoryItem> {

    private OnMenuCategoryClickListener onMenuCategoryClickListener;

    @NonNull
    @Override
    protected MenuCategoryViewHolder createViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.item_menu_category, viewGroup, false);
        MenuCategoryViewHolder viewHolder = new MenuCategoryViewHolder(itemView);

        if(onMenuCategoryClickListener != null) {
            viewHolder.setOnMenuCategoryClickListener(onMenuCategoryClickListener);
        }

        return viewHolder;
    }

    @Override
    protected void bindViewHolder(@NonNull MenuCategoryViewHolder viewHolder, @NonNull MenuCategoryItem item) {
        viewHolder.setMenuCategoryItem(item);
    }

    @Override
    public void setOnMenuCategoryClickListener(@NonNull OnMenuCategoryClickListener onMenuCategoryClickListener) {
        this.onMenuCategoryClickListener = onMenuCategoryClickListener;
    }

    @Override
    public void setSelectedPositionOfMenuCategories(int position) {
        handlingCategorySelection(position);
    }

    @Override
    public void selectDefaultCategory() {
        getItems().get(0).setSelect(true);
    }

    @NonNull
    public String getSelectedCategory() {
        for(MenuCategoryItem item : getItems()) {
            if(item.isSelect()) {
                return item.getCategoryName();
            }
        }

        return "";
    }

    private void handlingCategorySelection(int position) {
        for(int i=0; i<getItemCount(); i++) {
            MenuCategoryItem menuCategoryItem = getItem(i);
            if(i != position) {
                if(menuCategoryItem.isSelect()) {
                    menuCategoryItem.setSelect(false);
                    setItem(i, menuCategoryItem);
                }
            } else {
                menuCategoryItem.setSelect(true);
                setItem(i, menuCategoryItem);
            }
        }
    }

}
