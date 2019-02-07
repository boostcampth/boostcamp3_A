package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OverlapView extends FrameLayout {

    private static final int CREATE_LIMIT_COUNT = 3;

    private OverlapViewTurnHelper overlapViewTurnHelper;
    private OverlapViewAdapter overlapViewAdapter;
    private int position = 0;

    public OverlapView(@NonNull Context context) {
        super(context);

        setUp();
    }

    public OverlapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setUp();
    }

    private void setUp() {
        setUpTurnHelper();
    }

    private void setUpTurnHelper() {
        overlapViewTurnHelper = new OverlapViewTurnHelper();
        overlapViewTurnHelper.setOnTopViewMovieListener(this, new OnTopViewMoveStateListener() {
            @Override
            public void onDetachTopView() {
                Log.d("ch-yoon", "detachTopView");
            }

            @Override
            public void onNotDetachTopView() {
                Log.d("ch-yoon", "not detachTopView");
            }
        });
    }

    public void setOverlapViewAdapter(@NonNull OverlapViewAdapter overlapViewAdapter) {
        this.overlapViewAdapter = overlapViewAdapter;
        this.overlapViewAdapter.setOverlapAdapterDataObserver(new OverlapAdapterDataObserver() {
            @Override
            public void notifyDataSetChange() {
                initializeOverlapView();
            }
        });

        initializeOverlapView();
    }

    private void initializeOverlapView() {
        removeAllViews();
        createDefaultChildViews();
    }

    private void createDefaultChildViews() {
        if(hasDataOfAdapter()) {
            int createViewCount = calculateCreateViewCount();
            for(int i=0; i<createViewCount; i++) {
                OverlapViewViewHolder viewHolder = createViewHolderFromAdapter();
                setTagViewHolderToItemView(viewHolder);
                addViewToBottom(viewHolder.getItemView());
                loadDataOfBottomView();
            }
        }
    }

    private boolean hasDataOfAdapter() {
        return (overlapViewAdapter != null) && (0 <overlapViewAdapter.getItemCount());
    }

    private int calculateCreateViewCount() {
        return overlapViewAdapter.getItemCount() > 1 ? CREATE_LIMIT_COUNT : 1;
    }

    private OverlapViewViewHolder createViewHolderFromAdapter() {
        return overlapViewAdapter.onCreateView(LayoutInflater.from(getContext()), this);
    }

    private void setTagViewHolderToItemView(OverlapViewViewHolder viewHolder) {
        View itemView = viewHolder.getItemView();
        itemView.setTag(viewHolder);
    }

    private void addViewToBottom(View itemView) {
        addView(itemView, 0);
    }

    private int calculateAdapterItemPosition() {
        return (position + getChildCount()) % overlapViewAdapter.getItemCount();
    }

    private void loadDataOfBottomView() {
        View bottomView = getChildAt(0);
        OverlapViewViewHolder viewHolder = (OverlapViewViewHolder) bottomView.getTag();
        int adapterItemPosition = calculateAdapterItemPosition();
        overlapViewAdapter.onBindView(viewHolder, adapterItemPosition);
    }

}
