package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

class OverlapViewTurnHelper {

    private OverlapViewAnimationHelper overlapViewAnimationHelper;
    private OnTopViewDetachListener onTopViewDetachListener;

    private OverlapView overlapView;
    private RectF oldTopViewRect;
    private RectF newTopViewRect;
    private PointF downTouchCoordinates;
    private PointF moveTouchCoordinates;

    private boolean isTouchingTopView = false;
    private boolean isAnimationPlaying = false;

    OverlapViewTurnHelper() {
        setUp();
    }

    private void setUp() {
        overlapViewAnimationHelper = new OverlapViewDefaultAnimation();
        oldTopViewRect = new RectF();
        newTopViewRect = new RectF();
        downTouchCoordinates = new PointF();
        moveTouchCoordinates = new PointF();
    }

    @SuppressLint("ClickableViewAccessibility")
    void setOverlapView(@NonNull OverlapView overlapView) {
        this.overlapView = overlapView;
        this.overlapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!isAnimationPlaying) {
                    return handlingTopViewTouch(getTopView(), event);
                } else {
                    return false;
                }
            }
        });
    }

    void setonTopViewDetachListener(@NonNull final OnTopViewDetachListener onTopViewDetachListener) {
        this.onTopViewDetachListener = onTopViewDetachListener;
    }

    private View getTopView() {
        int topViewIndex = overlapView.getChildCount() - 1;
        return overlapView.getChildAt(topViewIndex);
    }

    private boolean handlingTopViewTouch(View topView, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recordCoordinatesOfTouchDown(event);
                recordCoordinatesOfOldTopView(topView);
                isTouchingTopView = isTouchTopView();

                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchingTopView) {
                    recordCoordinatesOfTouchMove(event);
                    PointF moveCoordinatesOfTopView = calculateMoveCoordinatesOfTopView();
                    moveTopViewToCoordinates(topView, moveCoordinatesOfTopView);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isTouchingTopView) {
                    recordCoordinatesOfNewTopView(topView);

                    if(isDetachTopView(topView)) {
                        handlingDetachAnimation(topView);
                    } else {
                        handlingNotDetachAnimation(topView);
                    }

                    isTouchingTopView = false;
                }

                break;
        }

        return true;
    }

    private void recordCoordinatesOfTouchDown(MotionEvent event) {
        downTouchCoordinates.set(event.getX(), event.getY());
    }

    private void recordCoordinatesOfTouchMove(MotionEvent event) {
        moveTouchCoordinates.set(event.getX(), event.getY());
    }

    private boolean isTouchTopView() {
        return oldTopViewRect.left <= downTouchCoordinates.x
                && downTouchCoordinates.x <= oldTopViewRect.right
                && oldTopViewRect.top <= downTouchCoordinates.y
                && downTouchCoordinates.y <= oldTopViewRect.bottom;
    }

    private void recordCoordinatesOfOldTopView(View topView) {
        oldTopViewRect.set(getNowTopViewRect(topView));
    }

    private void recordCoordinatesOfNewTopView(View topView) {
        newTopViewRect.set(getNowTopViewRect(topView));
    }

    private RectF getNowTopViewRect(View topView) {
        float left = topView.getX();
        float top = topView.getY();
        float right = left + topView.getWidth();
        float bottom = top + topView.getHeight();

        return new RectF(left, top, right, bottom);
    }

    private PointF calculateMoveCoordinatesOfTopView() {
        float movingDistanceX = moveTouchCoordinates.x - downTouchCoordinates.x;
        float movingDistanceY = moveTouchCoordinates.y - downTouchCoordinates.y;
        float moveCoordinatesX = oldTopViewRect.left + movingDistanceX;
        float moveCoordinatesY = oldTopViewRect.top + movingDistanceY;

        return new PointF(moveCoordinatesX, moveCoordinatesY);
    }

    private void moveTopViewToCoordinates(View topView, PointF moveTouchCoordinates) {
        topView.setX(moveTouchCoordinates.x);
        topView.setY(moveTouchCoordinates.y);
    }

    private void handlingDetachAnimation(View topView) {
        overlapViewAnimationHelper.executeDetachAnimation(topView,
                oldTopViewRect,
                newTopViewRect,
                new OnAnimationStateListener() {
                    @Override
                    public void onAnimationStart() {
                        isAnimationPlaying = true;
                    }

                    @Override
                    public void onAnimationEnd() {
                        isAnimationPlaying = false;

                        if(onTopViewDetachListener != null) {
                            onTopViewDetachListener.onDetachTopView();
                        }
                    }
                }
        );
    }

    private void handlingNotDetachAnimation(View topView) {
        overlapViewAnimationHelper.executeNotDetachAnimation(topView,
                oldTopViewRect,
                newTopViewRect,
                new OnAnimationStateListener() {
                    @Override
                    public void onAnimationStart() {
                        isAnimationPlaying = true;
                    }

                    @Override
                    public void onAnimationEnd() {
                        isAnimationPlaying = false;
                    }
                }
        );
    }

    private boolean isDetachTopView(View topView) {
        RectF passingLineRect = calculateDetachLine(topView);

        return passingLineRect.left <= oldTopViewRect.left || oldTopViewRect.right <= passingLineRect.right
                || passingLineRect.top <= oldTopViewRect.top || oldTopViewRect.bottom <= passingLineRect.bottom;
    }

    private RectF calculateDetachLine(View topView) {
        float allowableWidth = topView.getWidth() * 0.2f;
        float allowableHeight = topView.getHeight() * 0.2f;

        return new RectF(newTopViewRect.left + allowableWidth,
                newTopViewRect.top + allowableHeight,
                newTopViewRect.right - allowableWidth,
                newTopViewRect.bottom - allowableHeight);
    }

}
