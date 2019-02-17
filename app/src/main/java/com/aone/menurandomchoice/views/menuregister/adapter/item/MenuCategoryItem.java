package com.aone.menurandomchoice.views.menuregister.adapter.item;

public class MenuCategoryItem {

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
