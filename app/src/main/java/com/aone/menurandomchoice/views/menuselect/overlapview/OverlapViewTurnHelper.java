package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class OverlapViewTurnHelper {

    private OverlapView overlapView;
    private RectF oldTopViewRect;
    private RectF newTopViewRect;
    private PointF downTouchCoordinates;
    private PointF moveTouchCoordinates;
    private boolean isTouchingTopView = false;

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
                return handlingTopViewTouch(getTopView(), event, onTopViewMoveStateListener);
            }
        });
    }

    private View getTopView() {
        int topViewIndex = overlapView.getChildCount() - 1;
        return overlapView.getChildAt(topViewIndex);
    }

    private boolean handlingTopViewTouch(View topView, MotionEvent event, OnTopViewMoveStateListener onTopViewMoveStateListener) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recordCoordinatesOfTouchDown(event);
                recordCoordinatesOfOldTopView(topView);
                isTouchingTopView = isTouchTopView();

                break;
            case MotionEvent.ACTION_MOVE:
                if(isTouchingTopView) {
                    recordCoordinatesOfTouchMove(event);
                    PointF moveCoordinatesOfTopView = calculateMoveCoordinatesOfTopView();
                    moveTopViewToCoordinates(topView, moveCoordinatesOfTopView);
                }

                break;
            case MotionEvent.ACTION_UP:
                if(isTouchingTopView) {
                    recordCoordinatesOfNewTopView(topView);
                    recordCoordinatesOfTouchMove(event);
                    checkDetachOfTopView(topView, onTopViewMoveStateListener);
                }

                break;
        }

        return isTouchingTopView;
    }

    private void recordCoordinatesOfTouchDown(MotionEvent event) {
        downTouchCoordinates.set(event.getX(), event.getY());
    }

    private void recordCoordinatesOfTouchMove(MotionEvent event) {
        moveTouchCoordinates.set(event.getX(), event.getY());
    }

    private boolean isTouchTopView() {
        float touchDownX = downTouchCoordinates.x;
        float touchDownY = downTouchCoordinates.y;

        return oldTopViewRect.left <= touchDownX && touchDownX <= oldTopViewRect.right
                && oldTopViewRect.top <= touchDownY && touchDownY <= oldTopViewRect.bottom;
    }

    private void recordCoordinatesOfOldTopView(View topView) {
        oldTopViewRect.set(topView.getX(), topView.getY(), topView.getX() + topView.getWidth(), topView.getY() + topView.getHeight());
    }

    private void recordCoordinatesOfNewTopView(View topView) {
        newTopViewRect.set(topView.getX(), topView.getY(), topView.getX() + topView.getWidth(), topView.getY() + topView.getHeight());
    }

    private PointF calculateMoveCoordinatesOfTopView() {
        float movingDistanceX = moveTouchCoordinates.x - downTouchCoordinates.x;
        float movingDistanceY = moveTouchCoordinates.y - downTouchCoordinates.y;

        return new PointF(oldTopViewRect.left + movingDistanceX,
                oldTopViewRect.top + movingDistanceY);
    }

    private void moveTopViewToCoordinates(View topView, PointF moveTouchCoordinates) {
        topView.setX(moveTouchCoordinates.x);
        topView.setY(moveTouchCoordinates.y);
    }

    private void checkDetachOfTopView(View topView, OnTopViewMoveStateListener onTopViewMoveStateListener) {
        if(isDetachTopView(topView)) {
            onTopViewMoveStateListener.onDetachTopView();
        } else {
            onTopViewMoveStateListener.onNotDetachTopView();
        }

        moveTopViewToOldCoordinates(topView);
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

    private void moveTopViewToOldCoordinates(View topView) {
        topView.setX(oldTopViewRect.left);
        topView.setY(oldTopViewRect.top);
    }

}
