package com.baiiu.toucheventstudy.behaviors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * author: baiiu
 * date: on 17/4/18 19:21
 * description:
 */

public class ScrollUpDownBehavior extends FloatingActionButton.Behavior {

    public ScrollUpDownBehavior() {
    }

    public ScrollUpDownBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                                                 View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout,
                                                                                                child,
                                                                                                directTargetChild,
                                                                                                target,
                                                                                                nestedScrollAxes);
    }

    @Override public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target,
            int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0) {
            hide(child);
        } else if (dyConsumed < 0) {
            show(child);
        }

    }

    private static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new FastOutLinearInInterpolator();
    private static final int SHOW_HIDE_ANIM_DURATION = 200;
    private boolean mHideAnimationStart;
    private boolean mShowAnimationStart;

    private void hide(FloatingActionButton fab) {
        if (mHideAnimationStart) return;

        ViewCompat.animate(fab)
                .translationY(getBottom(fab))
                .setDuration(SHOW_HIDE_ANIM_DURATION)
                .setInterpolator(FAST_OUT_LINEAR_IN_INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override public void onAnimationStart(View view) {
                        mHideAnimationStart = true;
                    }

                    @Override public void onAnimationEnd(View view) {
                        mHideAnimationStart = false;
                    }
                })
                .start();

    }


    private void show(FloatingActionButton fab) {
        if (mShowAnimationStart) return;

        ViewCompat.animate(fab)
                .translationY(0)
                .setDuration(SHOW_HIDE_ANIM_DURATION)
                .setInterpolator(FAST_OUT_LINEAR_IN_INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override public void onAnimationStart(View view) {
                        mShowAnimationStart = true;
                    }

                    @Override public void onAnimationEnd(View view) {
                        mShowAnimationStart = false;
                    }
                })
                .start();

    }


    private float getBottom(FloatingActionButton fab) {
        int bottomMargin = ((ViewGroup.MarginLayoutParams) fab.getLayoutParams()).bottomMargin;
        return fab.getMeasuredHeight() + bottomMargin;
    }

}
