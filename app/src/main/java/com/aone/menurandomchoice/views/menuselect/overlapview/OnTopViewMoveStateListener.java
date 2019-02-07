package com.aone.menurandomchoice.views.menuselect.overlapview;

import android.graphics.RectF;
import android.view.View;

public interface OnTopViewMoveStateListener {

    void onDetachTopView(View topView, RectF newTopViewRect, RectF oldTopViewRect);

    void onNotDetachTopView(View topView, RectF newTopViewRect, RectF oldTopViewRect);

}
