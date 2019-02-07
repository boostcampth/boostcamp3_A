package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public abstract class OverlapViewAdapter<VH extends OverlapView.ViewHolder> {

    private OverlapAdapterDataObserver overlapAdapterDataObserver;
    private int topViewIndex = 0;

    void setOverlapAdapterDataObserver(@NonNull OverlapAdapterDataObserver overlapAdapterDataObserver) {
        this.overlapAdapterDataObserver = overlapAdapterDataObserver;
    }

    protected void notifyDataSetChange() {
        if(overlapAdapterDataObserver != null) {
            overlapAdapterDataObserver.notifyDataSetChange();
        }
    }

    void moveTopViewIndexToNext() {
        topViewIndex ++;
    }

    int getTopViewIndex() {
        return topViewIndex;
    }

    abstract protected VH onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup);

    abstract protected void onBindView(@NonNull VH viewHolder);

    abstract protected int getItemCount();

}
