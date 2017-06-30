package in.srain.cube.views.ptr;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;

public abstract class PtrDefaultHandler implements PtrHandler {

    public static boolean canChildScrollUp(View mTarget) {
        if (mTarget == null) return false;

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

    /**
     * Default implement for check can perform pull to refresh
     */
    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }
}