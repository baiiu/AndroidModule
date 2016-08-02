package com.baiiu.myapplication.module.ultraPtr;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * auther: baiiu
 * time: 16/8/2 02 22:01
 * description:
 */
public abstract class PtrRefreshHanlder implements PtrHandler {

    @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    public static boolean canChildScrollUp(View mTarget) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0
                        || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

}
