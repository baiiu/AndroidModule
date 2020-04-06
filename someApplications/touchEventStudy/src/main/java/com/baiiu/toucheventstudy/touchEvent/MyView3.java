package com.baiiu.toucheventstudy.touchEvent;

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
public class MyView3 extends AppCompatTextView {
    public MyView3(Context context) {
        super(context);
        init();
    }

    public MyView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                for (int i = 0; i < 10; i++) {
//                    android.util.Log.e("mLogU", "invalidate3: " + i);
//                    invalidate(); // 触发本view和view3重绘，因为与脏视图相交了
//                }
//
////                LogUtil.d("View --> requestLayout");
////                requestLayout();
//
////                ((ViewGroup) getParent()).removeViewInLayout(MyView.this);
////                setVisibility(GONE);
//            }
//        }, 10000);

    }

    @Override
    public void draw(Canvas canvas) {
        LogUtil.d("View3 --> draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.d("View3 --> onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.d("View3 --> onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtil.d("View3 --> onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.d("View3 --> dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("View3 --> onTouchEvent");
        return super.onTouchEvent(event);
    }
}
