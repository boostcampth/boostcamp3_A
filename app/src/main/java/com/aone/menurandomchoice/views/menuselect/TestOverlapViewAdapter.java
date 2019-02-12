package com.aone.menurandomchoice.views.menuselect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * OverlapView 가 정상적으로 작동되는지 확인하기 위한 테스트 코드이므로
 * 실제 구현과는 별개의 코드. 전부 삭제해야 함.
 */

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
        View menuItemView = layoutInflater.inflate(R.layout.item_menu_select_view, viewGroup, false);
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

    public TestData getTopViewData() {
        return itemList.get(getTopViewIndex());
    }

}
