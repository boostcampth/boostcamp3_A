package com.aone.menurandomchoice.views.menuselect;

import android.view.View;
import android.widget.ImageView;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapViewViewHolder;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;

public class MenuOverlapViewViewHolder extends OverlapViewViewHolder {

    private ImageView testImageView;

    protected MenuOverlapViewViewHolder(@NonNull View itemView) {
        super(itemView);

        testImageView = itemView.findViewById(R.id.test_menu_overlapView_imageView);
    }

    public void setItem(TestData item) {
        Glide.with(testImageView.getContext()).load(item.getImageResourceId()).into(testImageView);
    }

}
