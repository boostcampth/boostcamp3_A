package com.aone.menurandomchoice.views.menuselect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.views.menuselect.adapter.viewholder.MenuSelectOverlapViewViewHolder;
import com.aone.menurandomchoice.views.menuselect.overlapview.adapter.OverlapViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * OverlapView 가 정상적으로 작동되는지 확인하기 위한 테스트 코드이므로
 * 실제 구현과는 별개의 코드. 전부 삭제해야 함.
 */

public class MenuSelectOverlapViewAdapter
        extends OverlapViewAdapter<MenuSelectOverlapViewViewHolder>
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

    public MenuDetail getTopViewData() {
        return itemList.get(getTopViewIndex());
    }

}
