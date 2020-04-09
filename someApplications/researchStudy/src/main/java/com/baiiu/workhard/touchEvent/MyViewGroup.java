package com.baiiu.workhard.touchEvent;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.baiiu.library.LogUtil;

/**
 * author: baiiu
 * date: on 17/5/8 14:11
 * description:
 */
public class MyViewGroup extends FrameLayout {

    public MyViewGroup(@NonNull Context context) {
        super(context);
        init();
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        postDelayed(new Runnable() {
            @Override
            public void run() {

//                for (int i = 0; i < 10; i++) {
//                    android.util.Log.e("mLogU", "invalidateViewGroup: " + i);
//
//                    /*
//                        软件加速时全部子View重绘，因为与脏视图都相交了
//                        硬件加速时只会重绘标记为PFLAG_INVALIDATED才会重绘
//                     */
//                    invalidate(); // 软件加速时全部子View重绘，因为与脏视图都相交了
//                }

//                for (int i = 0; i < 10; i++) {
//                    LogUtil.d("View --> requestLayout: " + i);
//                    requestLayout();
//                }
            }
        }, 10000);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr,
                       @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.d("ViewGroup --> onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtil.d("ViewGroup --> onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        LogUtil.d("ViewGroup --> draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.d("ViewGroup --> onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        LogUtil.d("ViewGroup --> dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        LogUtil.d("ViewGroup --> drawChild: " + child.getClass() + ", " + drawingTime);

        boolean more = super.drawChild(canvas, child, drawingTime);


        return more;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.d("ViewGroup --> dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.d("ViewGroup --> onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("ViewGroup --> onTouchEvent");
        //return super.onTouchEvent(event);
        return true;
    }

}
