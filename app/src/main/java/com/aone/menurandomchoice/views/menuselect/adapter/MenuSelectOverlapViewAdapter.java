package com.aone.menurandomchoice.views.menuselect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.views.menuselect.adapter.viewholder.MenuSelectOverlapViewViewHolder;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.adapter.OverlapLoopViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MenuSelectOverlapViewAdapter
        extends OverlapLoopViewAdapter<MenuSelectOverlapViewViewHolder>
        implements MenuSelectOverlapViewAdapterContract.Model {

    private List<MenuDetail> itemList;

    public MenuSelectOverlapViewAdapter() {
        setUp();
    }

    private void setUp() {
        itemList = new ArrayList<>();
    }

    @Override
    public MenuSelectOverlapViewViewHolder onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View menuItemView = layoutInflater.inflate(R.layout.item_menu_select_view, viewGroup, false);
        return new MenuSelectOverlapViewViewHolder(menuItemView);
    }

    @Override
    public void onBindView(@NonNull MenuSelectOverlapViewViewHolder viewHolder) {
        int itemPosition = viewHolder.getItemPosition();
        viewHolder.setItem(itemList.get(itemPosition));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(@NonNull List<MenuDetail> itemList) {
        this.itemList = itemList;
        notifyDataSetChange();
    }

    @Nullable
    @Override
    public MenuDetail getTopViewData() {
        if(hasData()) {
            return itemList.get(getTopViewIndex());
        } else {
            return null;
        }
    }

    private boolean hasData() {
        return 0 < itemList.size();
    }

}
