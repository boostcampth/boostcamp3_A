package com.aone.menurandomchoice.views.menuselect.overlapview.animation;

import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;

public interface OverlapViewAnimationHelper {

    void executeDetachAnimation(@NonNull View topView,
                                @NonNull RectF firstCoordinatesOfView,
                                @NonNull RectF lastCoordinatesOfView,
                                float xDragRatio,
                                float yDragRatio,
                                @NonNull OnAnimationStateListener onAnimationStateListener);

    void executeNotDetachAnimation(@NonNull View topView,
                                   @NonNull RectF firstCoordinatesOfView,
                                   @NonNull RectF lastCoordinatesOfView,
                                   @NonNull OnAnimationStateListener onAnimationStateListener);

}
