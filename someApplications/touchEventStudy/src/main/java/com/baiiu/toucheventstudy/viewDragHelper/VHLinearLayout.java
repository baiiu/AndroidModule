package com.baiiu.toucheventstudy.viewDragHelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baiiu.toucheventstudy.R;

/**
 * author: baiiu
 * date: on 17/5/4 10:33
 * description:
 */
public class VHLinearLayout extends LinearLayout {

    private ViewDragHelper mDragHelper;
    private View tv_drag;
    private View tv_release;
    private View tv_edge;
    private int mOriginLeft;
    private int mOriginTop;

    public VHLinearLayout(Context context) {
        this(context, null);
    }

    public VHLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VHLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VHLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mDragHelper = ViewDragHelper.create(this, new MyDragHelper());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_ALL);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        tv_drag = findViewById(R.id.tv_drag);
        tv_release = findViewById(R.id.tv_release);
        tv_edge = findViewById(R.id.tv_edge);
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mOriginLeft = tv_release.getLeft();
        mOriginTop = tv_release.getTop();
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }

        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    private class MyDragHelper extends ViewDragHelper.Callback {

        @Override public boolean tryCaptureView(View child, int pointerId) {
            return child instanceof TextView;
        }

        @Override public int clampViewPositionHorizontal(View child, int left, int dx) {
            int leftBound = getPaddingLeft();
            int rightBound = getMeasuredWidth() - getPaddingRight() - child.getMeasuredWidth();
            return Math.min(Math.max(left, leftBound), rightBound);
        }


        @Override public int clampViewPositionVertical(View child, int top, int dy) {
            int topBound = getPaddingTop();
            int bottomBound = getMeasuredHeight() - child.getMeasuredHeight() - getPaddingBottom();
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == tv_release) {
                mDragHelper.settleCapturedViewAt(mOriginLeft, mOriginTop);
                invalidate();
            }
        }

        @Override public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(tv_edge, pointerId);
        }

    }

    @Override public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

}
