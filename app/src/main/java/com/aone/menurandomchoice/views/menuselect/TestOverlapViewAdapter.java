package com.aone.menurandomchoice.views.menuselect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class TestOverlapViewAdapter extends OverlapViewAdapter<TestOverlapViewViewHolder> {

    private List<TestData> itemList;

    TestOverlapViewAdapter() {
        setUp();
    }

    private void setUp() {
        itemList = new ArrayList<>();
    }

    @Override
    protected TestOverlapViewViewHolder onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View menuItemView = layoutInflater.inflate(R.layout.overlapview_test_layout, viewGroup, false);
        return new TestOverlapViewViewHolder(menuItemView);
    }

    @Override
    protected void onBindView(@NonNull TestOverlapViewViewHolder viewHolder) {
        int itemPosition = viewHolder.getItemPosition();
        viewHolder.setItem(itemList.get(itemPosition));
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
