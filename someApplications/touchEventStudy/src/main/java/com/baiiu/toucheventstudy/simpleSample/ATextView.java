package com.baiiu.toucheventstudy.simpleSample;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.baiiu.toucheventstudy.behaviors.SimpleBehavior;

/**
 * author: baiiu
 * date: on 17/4/24 18:13
 * description:
 */
@CoordinatorLayout.DefaultBehavior(SimpleBehavior.class)
public class ATextView extends AppCompatTextView {
    public ATextView(Context context) {
        super(context);
    }

    public ATextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ATextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
