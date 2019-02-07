package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public abstract class OverlapViewAdapter<VH extends OverlapViewViewHolder> {

    private OverlapAdapterDataObserver overlapAdapterDataObserver;
    private int topViewIndex = 0;

    public void setOverlapAdapterDataObserver(@NonNull OverlapAdapterDataObserver overlapAdapterDataObserver) {
        this.overlapAdapterDataObserver = overlapAdapterDataObserver;
    }

    public void notifyDataSetChange() {
        if(overlapAdapterDataObserver != null) {
            overlapAdapterDataObserver.notifyDataSetChange();
        }
    }

    public void moveTopViewIndexToNext() {
        topViewIndex++;
    }

    public int getTopViewIndex() {
        return topViewIndex;
    }

    abstract protected VH onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup);

    abstract protected void onBindView(@NonNull VH viewHolder, int position);

    abstract protected int getItemCount();

}
