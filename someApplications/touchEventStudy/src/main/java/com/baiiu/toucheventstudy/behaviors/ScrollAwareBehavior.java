package com.baiiu.toucheventstudy.behaviors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * author: baiiu
 * date: on 17/4/18 18:01
 * description:
 */
public class ScrollAwareBehavior extends FloatingActionButton.Behavior {
    public ScrollAwareBehavior() {
    }

    public ScrollAwareBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout,
                                                                                                child,
                                                                                                directTargetChild,
                                                                                                target,
                                                                                                nestedScrollAxes);
    }


    @Override public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
            final View target, final int dxConsumed, final int dyConsumed, final int dxUnconsumed,
            final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide(listener);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }

    }

    private FloatingActionButton.OnVisibilityChangedListener listener =
            new FloatingActionButton.OnVisibilityChangedListener() {
                @Override public void onHidden(FloatingActionButton fab) {
                    fab.setVisibility(View.INVISIBLE);
                }
            };
}
