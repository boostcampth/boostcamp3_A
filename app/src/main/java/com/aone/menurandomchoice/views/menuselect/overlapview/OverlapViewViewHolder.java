package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.view.View;

import androidx.annotation.NonNull;

public abstract class OverlapViewViewHolder {

    private View itemView;

    protected OverlapViewViewHolder(@NonNull View itemView) {
        this.itemView = itemView;
    }

    @NonNull
    public View getItemView() {
        return itemView;
    }

}
