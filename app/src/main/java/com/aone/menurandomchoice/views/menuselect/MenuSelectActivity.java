package com.aone.menurandomchoice.views.menuselect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapView;

import java.util.ArrayList;
import java.util.List;

public class MenuSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_select);

        OverlapView overlapView = findViewById(R.id.test_overlapView);

        List<TestData> testDataList = new ArrayList<>();
        testDataList.add(new TestData(R.drawable.test1));
        testDataList.add(new TestData(R.drawable.test2));
        testDataList.add(new TestData(R.drawable.test3));
        testDataList.add(new TestData(R.drawable.test4));
        testDataList.add(new TestData(R.drawable.test5));
        testDataList.add(new TestData(R.drawable.test6));
        testDataList.add(new TestData(R.drawable.test7));
        testDataList.add(new TestData(R.drawable.test8));

        MenuOverlapViewAdapter menuOverlapViewAdapter = new MenuOverlapViewAdapter();
        menuOverlapViewAdapter.setItemList(testDataList);
        overlapView.setOverlapViewAdapter(menuOverlapViewAdapter);

    }

}
