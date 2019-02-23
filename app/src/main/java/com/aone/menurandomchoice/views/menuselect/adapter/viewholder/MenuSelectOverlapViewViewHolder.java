package com.aone.menurandomchoice.views.menuselect.adapter.viewholder;

import android.view.View;

import com.aone.menurandomchoice.databinding.ItemMenuSelectViewBinding;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.utils.GlideUtil;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.OverlapLoopView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class MenuSelectOverlapViewViewHolder extends OverlapLoopView.ViewHolder {

    private ItemMenuSelectViewBinding itemMenuSelectViewBinding;

    public MenuSelectOverlapViewViewHolder(@NonNull View itemView) {
        super(itemView);

        itemMenuSelectViewBinding = DataBindingUtil.bind(itemView);
    }

    public void setItem(MenuDetail menuDetail) {
        itemMenuSelectViewBinding.setMenuDetail(menuDetail);
        GlideUtil.loadImageWithSkipCache(itemMenuSelectViewBinding.itemMenuSelectIv,
                menuDetail.getPhotoUrl(), itemMenuSelectViewBinding.itemMenuSelectViewProgressBar);
    }

}
