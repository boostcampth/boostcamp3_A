package com.aone.menurandomchoice.views.menuselect.overlapview.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;

public class OverlapViewDefaultAnimation implements OverlapViewAnimationHelper {

    private static final int PLAY_TIME = 200;

    @Override
    public void executeDetachAnimation(@NonNull final View topView,
                                       @NonNull final RectF firstCoordinatesOfView,
                                       @NonNull RectF lastCoordinatesOfView,
                                       @NonNull final OnAnimationStateListener onAnimationStateListener) {
        topView.animate()
                .translationXBy(lastCoordinatesOfView.left - firstCoordinatesOfView.left)
                .translationYBy(lastCoordinatesOfView.top - firstCoordinatesOfView.top)
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
