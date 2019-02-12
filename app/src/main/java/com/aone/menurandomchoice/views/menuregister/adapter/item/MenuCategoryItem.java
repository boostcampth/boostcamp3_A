package com.aone.menurandomchoice.views.menuregister.adapter.item;

import java.util.Observable;

public class MenuCategoryItem extends Observable {

    private String categoryName;
    private boolean isSelect;

    public MenuCategoryItem(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

}
