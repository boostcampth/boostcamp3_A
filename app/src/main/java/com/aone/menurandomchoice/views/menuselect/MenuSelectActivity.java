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


        /**
         * 아래에 있는 코드들은 OverlapView 테스트를 위한 샘플 코드
         *
         * 실제 구현시에는 아래의 코드들을 전부 삭제하고
         * 데이터를 서버에 요청하고, 서버로부터 데이터를 받아와서 처리해야함.
         *
         * OverlapView 는 사진 선택이 아닌, 별도의 선택 버튼을 추가적으로 만들어서
         * OverlapViewAdapter 로부터 data 를 가져와야 함.
         *
         * ex) testOverlapViewAdapter.getTopViewData();
         */
        OverlapView overlapView = findViewById(R.id.test_overlapView);
        TestOverlapViewAdapter testOverlapViewAdapter = new TestOverlapViewAdapter();
        testOverlapViewAdapter.setItemList(getTestDataList());
        overlapView.setOverlapViewAdapter(testOverlapViewAdapter);
    }

    private List<TestData> getTestDataList() {
        List<TestData> testDataList = new ArrayList<>();
        testDataList.add(new TestData(R.drawable.test1));
        testDataList.add(new TestData(R.drawable.test2));
        testDataList.add(new TestData(R.drawable.test3));
        testDataList.add(new TestData(R.drawable.test4));
        testDataList.add(new TestData(R.drawable.test5));

        return testDataList;
    }

}
