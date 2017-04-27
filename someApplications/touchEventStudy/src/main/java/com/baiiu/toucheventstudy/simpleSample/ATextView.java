package com.baiiu.toucheventstudy.simpleSample;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import com.baiiu.toucheventstudy.behaviors.SimpleBehavior;

/**
 * author: baiiu
 * date: on 17/4/24 18:13
 * description:
 */
@CoordinatorLayout.DefaultBehavior(SimpleBehavior.class)
public class ATextView extends android.support.v7.widget.AppCompatTextView {
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
