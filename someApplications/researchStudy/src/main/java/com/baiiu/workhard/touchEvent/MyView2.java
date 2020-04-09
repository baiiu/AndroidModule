package com.baiiu.workhard.touchEvent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.baiiu.library.LogUtil;

/**
 * author: baiiu
 * date: on 17/5/8 14:13
 * description:
 */
public class MyView2 extends AppCompatTextView {
    public MyView2(Context context) {
        super(context);
        init();
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    public void draw(Canvas canvas) {
        LogUtil.d("View2 --> draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.d("View2 --> onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.d("View2 --> onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtil.d("View2 --> onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.d("View2 --> dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("View2 --> onTouchEvent");
        return super.onTouchEvent(event);
    }
}
