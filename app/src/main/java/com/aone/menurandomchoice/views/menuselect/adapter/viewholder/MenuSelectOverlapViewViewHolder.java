package com.aone.menurandomchoice.views.menuselect.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ItemMenuSelectViewBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.utils.GlideUtil;
import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

/**
 * OverlapView 가 정상적으로 작동되는지 확인하기 위한 테스트 코드이므로
 * 실제 구현과는 별개의 코드. 전부 삭제해야 함.
 */
public class MenuSelectOverlapViewViewHolder extends OverlapView.ViewHolder {

    private ItemMenuSelectViewBinding itemMenuSelectViewBinding;

    public MenuSelectOverlapViewViewHolder(@NonNull View itemView) {
        super(itemView);

        itemMenuSelectViewBinding = DataBindingUtil.bind(itemView);
    }

    public void setItem(MenuDetail menuDetail) {
        itemMenuSelectViewBinding.setMenuDetail(menuDetail);
        GlideUtil.loadImageWithSkipCache(itemMenuSelectViewBinding.itemMenuSelectIv, menuDetail.getUrl());


    }

}
