package com.aone.menurandomchoice.views.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aone.menurandomchoice.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);

        Button btnUpload = findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_start:
                //Move to Random Choice Activity
                break;

            case R.id.btn_upload:
                //Move to Upload Store Information Activity
                break;

            default:
                return;
        }
    }
}
