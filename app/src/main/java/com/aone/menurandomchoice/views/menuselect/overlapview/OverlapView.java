package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OverlapView extends FrameLayout {

    private static final int CREATE_LIMIT_COUNT = 3;

    private OverlapViewAdapter overlapViewAdapter;

    public OverlapView(@NonNull Context context) {
        super(context);

        setUpViewTurnHelper();
    }

    public OverlapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setUpViewTurnHelper();
    }

    private void setUpViewTurnHelper() {
        OverlapViewTurnHelper overlapViewTurnHelper = new OverlapViewTurnHelper();
        overlapViewTurnHelper.setOverlapView(this);
        overlapViewTurnHelper.setonTopViewDetachListener(new OnTopViewDetachListener() {
            @Override
            public void onDetachTopView() {
                turnTopViewToBottom();
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
        if(hasAdapterData()) {
            int createViewCount = calculateCreateViewCount();
            for(int i=0; i<createViewCount; i++) {
                OverlapView.ViewHolder viewHolder = createViewHolderFromAdapter();
                setTagViewHolderToItemView(viewHolder);
                addViewToBottom(viewHolder.getItemView());
                preloadDataOfBottomView();
            }
        }
    }

    private boolean hasAdapterData() {
        return overlapViewAdapter != null && 0 < overlapViewAdapter.getItemCount();
    }

    private int calculateCreateViewCount() {
        return overlapViewAdapter.getItemCount() > 1 ? CREATE_LIMIT_COUNT : 1;
    }

    private OverlapView.ViewHolder createViewHolderFromAdapter() {
        return overlapViewAdapter.onCreateView(LayoutInflater.from(getContext()), this);
    }

    private void setTagViewHolderToItemView(OverlapView.ViewHolder viewHolder) {
        View itemView = viewHolder.getItemView();
        itemView.setTag(viewHolder);
    }

    private void addViewToBottom(View itemView) {
        addView(itemView, 0);
    }

    private void preloadDataOfBottomView() {
        View bottomView = getChildAt(0);
        OverlapView.ViewHolder viewHolder = (OverlapView.ViewHolder) bottomView.getTag();
        int adapterItemPosition = calculateAdapterItemPosition();
        viewHolder.setItemPosition(adapterItemPosition);
        overlapViewAdapter.onBindView(viewHolder);
    }

    private int calculateAdapterItemPosition() {
        return (overlapViewAdapter.getTopViewIndex() + getChildCount() - 1) % overlapViewAdapter.getItemCount();
    }

    private void turnTopViewToBottom() {
        moveTopViewPositionToNext();
        moveTopViewToBottom();
        preloadDataOfBottomView();
    }

    private void moveTopViewPositionToNext() {
        overlapViewAdapter.moveTopViewIndexToNext();
    }

    private void moveTopViewToBottom() {
        View topView = removeTopView();
        addViewToBottom(topView);
    }

    private View removeTopView() {
        int topChildViewIndex = getChildCount() - 1;
        View topView = getChildAt(topChildViewIndex);
        removeView(topView);

        return topView;
    }

    public static abstract class ViewHolder {

        private View itemView;
        private int itemPosition;

        protected ViewHolder(@NonNull View itemView) {
            this.itemView = itemView;
        }

        void setItemPosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        public int getItemPosition() {
            return itemPosition;
        }

        @NonNull
        View getItemView() {
            return itemView;
        }

    }

}
