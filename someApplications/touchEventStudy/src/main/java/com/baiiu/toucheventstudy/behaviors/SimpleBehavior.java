package com.baiiu.toucheventstudy.behaviors;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baiiu.toucheventstudy.simpleSample.TempView;

/**
 * author: baiiu
 * date: on 17/4/18 09:54
 * description:
 */
public class SimpleBehavior extends CoordinatorLayout.Behavior<TextView> {

    private final int width;

    public SimpleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics display = context.getResources()
                .getDisplayMetrics();
        width = display.widthPixels;
    }

    // child 是否依赖于 dependency
    @Override public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof TempView;
    }

    // 当dependency变化时，回调此方法，child可以做相应的变化
    @Override public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        int top = dependency.getTop();
        int left = dependency.getLeft();

        layoutChild(child, width - left - child.getMeasuredWidth(), top);
        return true;
    }

    private void layoutChild(View view, int x, int y) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.leftMargin = x;
        params.topMargin = y;
        view.setLayoutParams(params);
    }
}
