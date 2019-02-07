package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public abstract class OverlapViewAdapter<VH extends OverlapView.ViewHolder> {

    private OverlapViewAdapterDataObserver overlapViewAdapterDataObserver;

    private int topViewIndex = 0;

    void setOverlapViewAdapterDataObserver(@NonNull OverlapViewAdapterDataObserver overlapViewAdapterDataObserver) {
        this.overlapViewAdapterDataObserver = overlapViewAdapterDataObserver;
    }

    protected void notifyDataSetChange() {
        if(overlapViewAdapterDataObserver != null) {
            overlapViewAdapterDataObserver.notifyDataSetChange();
        }
    }

    void moveTopViewIndexToNext() {
        topViewIndex ++;
    }

    protected int getTopViewIndex() {
        return topViewIndex;
    }

    abstract protected VH onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup);

    abstract protected void onBindView(@NonNull VH viewHolder);

    abstract protected int getItemCount();

}
