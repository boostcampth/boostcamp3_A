package com.aone.menurandomchoice.views.menuselect;

import androidx.annotation.NonNull;

import android.os.Bundle;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityMenuSelectBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapView;

import java.util.ArrayList;
import java.util.List;

public class MenuSelectActivity
        extends BaseActivity<ActivityMenuSelectBinding, MenuSelectContract.View, MenuSelectContract.Presenter>
        implements MenuSelectContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpPresenterToDataBinding();
        OverlapView overlapView = findViewById(R.id.test_overlapView);
        TestOverlapViewAdapter testOverlapViewAdapter = new TestOverlapViewAdapter();
        testOverlapViewAdapter.setItemList(getTestDataList());
        overlapView.setOverlapViewAdapter(testOverlapViewAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu_select;
    }

    @NonNull
    @Override
    protected MenuSelectContract.Presenter setUpPresenter() {
        return new MenuSelectPresenter();
    }

    @NonNull
    @Override
    protected MenuSelectContract.View getView() {
        return this;
    }

    private void setUpPresenterToDataBinding() {
        getDataBinding().setPresenter(getPresenter());
    }
    
    private List<TestData> getTestDataList() {
        List<TestData> testDataList = new ArrayList<>();
        testDataList.add(new TestData(R.drawable.test1, "김치말이국수",
                "이거 완전맛있어용~!!! 먹어보세용~!!!! 두말하면 잔소리~!!",
                "15,000",
                "한식"));
        testDataList.add(new TestData(R.drawable.test2, "얼큰순대국",
                "숙취 해소에는 이만한게 없지용~!!",
                "8,000",
                "양식"));
        testDataList.add(new TestData(R.drawable.test3, "이름이 엄청나게 긴 메뉴입니당",
                "메뉴 설명 짧게도 해보고",
                "15,000",
                "중식"));
        testDataList.add(new TestData(R.drawable.test4, "라면",
                "이건 엄청나게 기니까 메뉴 설명이 ...으로 표시되지 않을까 싶은데요 과연 어디까지 표현이 될까요",
                "15,000,000",
                "한식"));
        testDataList.add(new TestData(R.drawable.test5, "얼쑤",
                "딩딩딩 디기디기디기디 딩딩!",
                "8,000",
                "한식"));

        return testDataList;
    }

}
