package com.aone.menurandomchoice.views.menuselect;

import android.view.View;
import android.widget.ImageView;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;

/**
 * OverlapView 가 정상적으로 작동되는지 확인하기 위한 테스트 코드이므로
 * 실제 구현과는 별개의 코드. 전부 삭제해야 함.
 */
public class TestOverlapViewViewHolder extends OverlapView.ViewHolder {

    private ImageView testImageView;

    TestOverlapViewViewHolder(@NonNull View itemView) {
        super(itemView);
        testImageView = itemView.findViewById(R.id.test_menu_overlapView_imageView);
    }

    public void setItem(TestData item) {
        Glide.with(testImageView.getContext()).load(item.getImageResourceId()).apply(new RequestOptions().centerCrop()).into(testImageView);
    }

}
