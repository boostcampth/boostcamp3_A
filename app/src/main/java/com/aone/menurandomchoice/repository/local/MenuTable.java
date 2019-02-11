package com.aone.menurandomchoice.repository.local;

public enum MenuTable {

    TABLE_NAME("MENU"),
    MENU_IDX("menu_idx"),
    MENU_NAME("name"),
    MENU_PHOTO_URL("photo_url"),
    MENU_PRICE("price"),
    MENU_DESCRIPTION("description"),
    MENU_CATEGORY("category"),
    MENU_SEQUENCE("sequence");

    private String columnName;

    MenuTable(String columnName){
        this.columnName = columnName;
    }
    String getColumnName(){
        return this.columnName;
    }
}
