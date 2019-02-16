package com.aone.menurandomchoice.views.menuselect.overlapview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aone.menurandomchoice.views.menuselect.overlapview.OverlapView;

import androidx.annotation.NonNull;

public abstract class OverlapViewAdapter<VH extends OverlapView.ViewHolder> {

    private OverlapViewAdapterDataObserver overlapViewAdapterDataObserver;

    private int topViewIndex = 0;

    public void setOverlapViewAdapterDataObserver(@NonNull OverlapViewAdapterDataObserver overlapViewAdapterDataObserver) {
        this.overlapViewAdapterDataObserver = overlapViewAdapterDataObserver;
    }

    public void moveTopViewIndexToNext() {
        topViewIndex ++;
    }

    public int getTopViewIndex() {
        return topViewIndex;
    }

    protected void notifyDataSetChange() {
        if(overlapViewAdapterDataObserver != null) {
            overlapViewAdapterDataObserver.notifyDataSetChange();
        }
    }

    abstract public VH onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup);

    abstract public void onBindView(@NonNull VH viewHolder);

    abstract public int getItemCount();

}
