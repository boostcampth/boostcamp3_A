package com.aone.menurandomchoice.views.menuselect.overlapview.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RectF;
import android.view.View;

import com.aone.menurandomchoice.views.menuselect.overlapview.helper.DetachStateCalculator;

import androidx.annotation.NonNull;

public class OverlapViewDefaultAnimation implements OverlapViewAnimationHelper {

    private static final int PLAY_TIME = 200;
    private static final float TRANSLATION_SENSITIVITY = 0.3f;

    @Override
    public void executeDetachAnimation(@NonNull final View topView,
                                       @NonNull final RectF firstCoordinatesOfView,
                                       @NonNull RectF lastCoordinatesOfView,
                                       float xDragRatio,
                                       float yDragRatio,
                                       @NonNull final OnAnimationStateListener onAnimationStateListener) {
        float translationDistance = topView.getWidth() * TRANSLATION_SENSITIVITY;
        float xDistance = translationDistance * xDragRatio;
        float yDistance = translationDistance * yDragRatio;

        topView.animate()
                .translationXBy(lastCoordinatesOfView.left + xDistance)
                .translationYBy(lastCoordinatesOfView.top + yDistance)
                .alpha(0)
                .setDuration(PLAY_TIME)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        onAnimationStateListener.onAnimationStart();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        topView.setRotation(0);
                        topView.setAlpha(1);
                        topView.setX(firstCoordinatesOfView.left);
                        topView.setY(firstCoordinatesOfView.top);
                        onAnimationStateListener.onAnimationEnd();
                    }
                })
                .start();
    }

    @Override
    public void executeNotDetachAnimation(@NonNull View topView,
                                          @NonNull RectF firstCoordinatesOfView,
                                          @NonNull RectF lastCoordinatesOfView,
                                          @NonNull final OnAnimationStateListener onAnimationStateListener) {
        topView.animate()
                .translationX(firstCoordinatesOfView.left)
                .translationY(firstCoordinatesOfView.top)
                .rotation(0)
                .setDuration(PLAY_TIME)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        onAnimationStateListener.onAnimationStart();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        onAnimationStateListener.onAnimationEnd();
                    }
                })
                .start();
    }

}
