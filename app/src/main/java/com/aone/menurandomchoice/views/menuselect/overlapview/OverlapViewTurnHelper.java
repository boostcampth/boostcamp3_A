package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

class OverlapViewTurnHelper {

    enum ViewTouchArea {
        TOP, BOTTOM, NONE
    }

    private static final float ROTATE_SENSITIVITY = 0.03f;
    private static final float DETACH_ALLOW_RANGE = 0.4f;

    private OverlapViewAnimationHelper overlapViewAnimationHelper;
    private OnTopViewDetachListener onTopViewDetachListener;

    private OverlapView overlapView;
    private RectF oldTopViewRect;
    private RectF newTopViewRect;
    private PointF downTouchCoordinates;
    private PointF moveTouchCoordinates;
    private ViewTouchArea viewTouchArea;

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
        viewTouchArea = ViewTouchArea.NONE;
    }

    @SuppressLint("ClickableViewAccessibility")
    void setOverlapView(@NonNull OverlapView overlapView) {
        this.overlapView = overlapView;
        this.overlapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!isAnimationPlaying) {
                    View topView = getTopView();
                    if(topView != null) {
                        return handlingTopViewTouch(topView, event);
                    }
                }

                return false;
            }
        });
    }

    void setonTopViewDetachListener(@NonNull OnTopViewDetachListener onTopViewDetachListener) {
        this.onTopViewDetachListener = onTopViewDetachListener;
    }

    private View getTopView() {
        int topViewIndex = overlapView.getChildCount() - 1;
        if(0 <= topViewIndex) {
            return overlapView.getChildAt(topViewIndex);
        } else {
            return null;
        }
    }

    private boolean handlingTopViewTouch(View topView, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchingTopView = isTouchTopView(topView, event);
                if(isTouchingTopView) {
                    recordCoordinatesOfTouchDown(event);
                    recordCoordinatesOfOldTopView(topView);
                    recordViewTouchArea();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchingTopView) {
                    recordCoordinatesOfTouchMove(event);
                    PointF coordinatesToMove = calculateCoordinatesToMove();
                    moveTopViewToCoordinates(topView, coordinatesToMove);
                    rotateTopView(topView);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isTouchingTopView) {
                    isTouchingTopView = false;
                    recordCoordinatesOfNewTopView(topView);
                    if(isDetachTopView(topView) && isMoreChildView()) {
                        requestDetachAnimationToHelper(topView);
                    } else {
                        requestNotDetachAnimationToHelper(topView);
                    }
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

    private void recordViewTouchArea() {
        if(downTouchCoordinates.y <= oldTopViewRect.centerY()) {
            viewTouchArea = ViewTouchArea.TOP;
        } else {
            viewTouchArea = ViewTouchArea.BOTTOM;
        }
    }

    private boolean isTouchTopView(View topView, MotionEvent event) {
        RectF topViewRect = getNowTopViewRect(topView);
        float x = event.getX();
        float y = event.getY();

        return topViewRect.left <= x && x <= topViewRect.right
                && topViewRect.top <= y && y <= topViewRect.bottom;
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

    private PointF calculateCoordinatesToMove() {
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

    private void rotateTopView(View topView) {
        float rotationAngle = (downTouchCoordinates.x - moveTouchCoordinates.x) * ROTATE_SENSITIVITY;
        switch (viewTouchArea) {
            case TOP:
                topView.setRotation(rotationAngle * -1);
                break;
            case BOTTOM:
                topView.setRotation(rotationAngle);
                break;
        }
    }

    private void requestDetachAnimationToHelper(View topView) {
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

    private void requestNotDetachAnimationToHelper(View topView) {
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
        float allowableWidth = topView.getWidth() * DETACH_ALLOW_RANGE;
        float allowableHeight = topView.getHeight() * DETACH_ALLOW_RANGE;

        float detachLineOfLeft = newTopViewRect.left + allowableWidth;
        float detachLineOfTop = newTopViewRect.top + allowableHeight;
        float detachLineOfRight = newTopViewRect.right - allowableWidth;
        float detachLineOfBottom = newTopViewRect.bottom - allowableHeight;

        return detachLineOfLeft <= oldTopViewRect.left || oldTopViewRect.right <= detachLineOfRight
                || detachLineOfTop <= oldTopViewRect.top || oldTopViewRect.bottom <= detachLineOfBottom;
    }

    private boolean isMoreChildView() {
        return 1 < overlapView.getChildCount();
    }

}
