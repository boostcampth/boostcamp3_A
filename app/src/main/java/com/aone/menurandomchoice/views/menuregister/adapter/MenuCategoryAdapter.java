package com.aone.menurandomchoice.views.menuregister.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.base.adapter.BaseRecyclerViewAdapter;
import com.aone.menurandomchoice.views.menuregister.adapter.item.MenuCategoryItem;
import com.aone.menurandomchoice.views.menuregister.adapter.viewholder.MenuCategoryViewHolder;
import com.aone.menurandomchoice.views.menuregister.adapter.viewholder.OnItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;


public class MenuCategoryAdapter extends BaseRecyclerViewAdapter<MenuCategoryItem, MenuCategoryViewHolder> {

    @NonNull
    @Override
    protected MenuCategoryViewHolder createViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.item_menu_category, viewGroup, false);
        MenuCategoryViewHolder viewHolder = new MenuCategoryViewHolder(itemView);
        viewHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int position) {
                handlingCategorySelection(position);
            }
        });

        return viewHolder;
    }

    @Override
    protected void bindViewHolder(@NonNull MenuCategoryViewHolder viewHolder, @NonNull MenuCategoryItem item) {
        viewHolder.setMenuCategoryItem(item);
    }

    @Override
    public void setItems(@NonNull List<MenuCategoryItem> items) {
        setSelectionDefaultCategory(items);

        super.setItems(items);
    }

    private void setSelectionDefaultCategory(List<MenuCategoryItem> items) {
        items.get(0).setSelect(true);
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
