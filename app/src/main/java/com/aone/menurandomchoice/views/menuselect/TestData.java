package com.aone.menurandomchoice.views.menuselect;

/**
 * OverlapView 가 정상적으로 작동되는지 확인하기 위한 테스트 코드이므로
 * 실제 구현과는 별개의 코드. 전부 삭제해야 함.
 */
public class TestData {

    private int imageResourceId;
    private String menuTitle;
    private String menuGuide;
    private String price;
    private String category;

    public TestData(int imageResourceId, String menuTitle, String menuGuide, String price, String category) {
        this.imageResourceId = imageResourceId;
        this.menuTitle = menuTitle;
        this.menuGuide = menuGuide;
        this.price = price;
        this.category = category;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public String getMenuGuide() {
        return menuGuide;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
