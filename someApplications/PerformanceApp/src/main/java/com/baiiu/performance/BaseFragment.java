package com.baiiu.performance;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * author: baiiu
 * date: on 16/10/25 14:11
 * description:
 */

public class BaseFragment extends Fragment {
    protected Context mContext;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Override public void onDestroy() {
        UIUtil.getRefWatcher(mContext)
                .watch(this);
        super.onDestroy();
    }

}
