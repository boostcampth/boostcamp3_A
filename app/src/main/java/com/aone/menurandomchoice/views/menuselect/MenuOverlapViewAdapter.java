package com.aone.menurandomchoice.views.menuselect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class MenuOverlapViewAdapter extends OverlapViewAdapter<MenuOverlapViewViewHolder> {

    private List<TestData> itemList;

    MenuOverlapViewAdapter() {
        setUp();
    }

    private void setUp() {
        itemList = new ArrayList<>();
    }

    @Override
    protected MenuOverlapViewViewHolder onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View menuItemView = layoutInflater.inflate(R.layout.overlapview_test_layout, viewGroup, false);
        return new MenuOverlapViewViewHolder(menuItemView);
    }

    @Override
    protected void onBindView(@NonNull MenuOverlapViewViewHolder viewHolder, int position) {
        viewHolder.setItem(itemList.get(position));
    }

    @Override
    protected int getItemCount() {
        return itemList.size();
    }

    public void setItemList(@NonNull List<TestData> itemList) {
        this.itemList = itemList;
        notifyDataSetChange();
    }

}
