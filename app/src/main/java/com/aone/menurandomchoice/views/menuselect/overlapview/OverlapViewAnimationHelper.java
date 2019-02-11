package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;

interface OverlapViewAnimationHelper {

    void executeDetachAnimation(@NonNull View topView,
                                @NonNull RectF firstCoordinatesOfView,
                                @NonNull RectF lastCoordinatesOfView,
                                @NonNull OnAnimationStateListener onAnimationStateListener);

    void executeNotDetachAnimation(@NonNull View topView,
                                   @NonNull RectF firstCoordinatesOfView,
                                   @NonNull RectF lastCoordinatesOfView,
                                   @NonNull OnAnimationStateListener onAnimationStateListener);

}
