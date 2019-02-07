package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class OverlapViewTurnHelper {

    private OverlapView overlapView;
    private RectF oldTopViewRect;
    private RectF newTopViewRect;
    private PointF downTouchCoordinates;
    private PointF moveTouchCoordinates;
    private boolean isTouchTopView = false;

    OverlapViewTurnHelper() {
        setUp();
    }

    private void setUp() {
        oldTopViewRect = new RectF();
        newTopViewRect = new RectF();
        downTouchCoordinates = new PointF();
        moveTouchCoordinates = new PointF();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnTopViewMovieListener(OverlapView overlapView, final OnTopViewMoveStateListener onTopViewMoveStateListener) {
        this.overlapView = overlapView;
        overlapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return handlingTopViewMove(getTopView(), event, onTopViewMoveStateListener);
            }
        });
    }

    private View getTopView() {
        int topViewIndex = overlapView.getChildCount() - 1;
        return overlapView.getChildAt(topViewIndex);
    }

    private boolean handlingTopViewMove(View topView, MotionEvent event, OnTopViewMoveStateListener onTopViewMoveStateListener) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recordDownTouchCoordinates(event);
                isTouchTopView = isTouchTopView(topView);
                if(isTouchTopView) {
                    recordTopViewRect(topView);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isTouchTopView) {
                    recordMoveTouchCoordinates(event);
                    PointF moveCoordinatesOfTopView = calculateMoveCoordinatesOfTopView();
                    moveTopViewToCoordinates(topView, moveCoordinatesOfTopView);
                    recordNewTopViewRect(topView);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isTouchTopView) {
                    recordMoveTouchCoordinates(event);
                    recordNewTopViewRect(topView);
                    if(isDetachTopView(topView)) {
                        onTopViewMoveStateListener.onDetachTopView(topView, newTopViewRect, oldTopViewRect);
                    } else {
                        onTopViewMoveStateListener.onNotDetachTopView(topView, newTopViewRect, oldTopViewRect);
                    }
                }
                break;
        }

        return isTouchTopView;
    }

    private void recordDownTouchCoordinates(MotionEvent event) {
        downTouchCoordinates.set(event.getX(), event.getY());
    }

    private void recordMoveTouchCoordinates(MotionEvent event) {
        moveTouchCoordinates.set(event.getX(), event.getY());
    }

    private void recordTopViewRect(View topView) {
        oldTopViewRect.set(topView.getLeft(), topView.getTop(), topView.getRight(), topView.getBottom());
    }

    private boolean isTouchTopView(View topView) {
        float startXOfTopChildView = topView.getX();
        float endXOfTopChildView = startXOfTopChildView + topView.getWidth();
        float startYOfTopChildView = topView.getY();
        float endYOfTopChildView = startYOfTopChildView + topView.getHeight();

        float touchDownX = downTouchCoordinates.x;
        float touchDownY = downTouchCoordinates.y;

        return startXOfTopChildView <= touchDownX && touchDownX <= endXOfTopChildView
                && startYOfTopChildView <= touchDownY && touchDownY <= endYOfTopChildView;
    }

    private PointF calculateMoveCoordinatesOfTopView() {
        float movingDistanceX = moveTouchCoordinates.x - downTouchCoordinates.x;
        float movingDistanceY = moveTouchCoordinates.y - downTouchCoordinates.y;

        return new PointF(oldTopViewRect.left + movingDistanceX, oldTopViewRect.top + movingDistanceY);
    }

    private void moveTopViewToCoordinates(View topView, PointF moveTouchCoordinates) {
        topView.setX(moveTouchCoordinates.x);
        topView.setY(moveTouchCoordinates.y);
    }

    private void recordNewTopViewRect(View topView) {
        newTopViewRect.set(topView.getLeft(), topView.getTop(), topView.getRight(), topView.getBottom());
    }

    private boolean isDetachTopView(View topView) {
        float nowTopViewCenterX = topView.getX() + (topView.getWidth() / 2f);
        float nowTopViewCenterY = topView.getY() + (topView.getHeight() / 2f);

        return nowTopViewCenterX <= oldTopViewRect.left || oldTopViewRect.right <= nowTopViewCenterX
                || nowTopViewCenterY <= oldTopViewRect.top || oldTopViewRect.bottom <= nowTopViewCenterY;

    }

}
