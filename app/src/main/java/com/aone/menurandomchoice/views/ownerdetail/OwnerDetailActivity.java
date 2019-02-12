package com.aone.menurandomchoice.views.ownerdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.menuregister.MenuRegisterActivity;

public class OwnerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_detail);

        Intent intent = new Intent(this, MenuRegisterActivity.class);
        startActivity(intent);
    }

}
