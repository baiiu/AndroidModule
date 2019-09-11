package com.baiiu.toucheventstudy.touchEvent;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr,
            @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.d("ViewGroup --> dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
        //return true;
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.d("ViewGroup --> onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("ViewGroup --> onTouchEvent");
        //return super.onTouchEvent(event);
        return true;
    }

}
