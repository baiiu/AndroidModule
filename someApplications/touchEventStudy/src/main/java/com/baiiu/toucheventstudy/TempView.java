package com.baiiu.toucheventstudy;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: baiiu
 * date: on 17/4/18 10:11
 * description:
 */
public class TempView extends View {

    private int lastX;
    private int lastY;

    public TempView(Context context) {
        super(context);
    }

    public TempView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TempView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TempView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        int startX = (int) event.getRawX();
        int startY = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                ViewGroup.MarginLayoutParams params = (CoordinatorLayout.MarginLayoutParams) getLayoutParams();
                params.leftMargin = params.leftMargin + startX - lastX;
                params.topMargin = params.topMargin + startY - lastY;
                setLayoutParams(params);

                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        lastX = startX;
        lastY = startY;

        return true;
    }
}
