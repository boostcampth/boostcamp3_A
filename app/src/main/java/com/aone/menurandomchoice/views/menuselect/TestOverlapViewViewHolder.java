package com.aone.menurandomchoice.views.menuselect;

import android.view.View;
import android.widget.ImageView;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;

public class TestOverlapViewViewHolder extends OverlapView.ViewHolder {

    private ImageView testImageView;

    TestOverlapViewViewHolder(@NonNull View itemView) {
        super(itemView);
        testImageView = itemView.findViewById(R.id.test_menu_overlapView_imageView);
    }

    public void setItem(TestData item) {
        Glide.with(testImageView.getContext()).load(item.getImageResourceId()).into(testImageView);
    }

}
