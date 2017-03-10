package com.baiiu.commontool.base;

import android.support.v4.app.Fragment;
import com.baiiu.commontool.app.UIUtil;

/**
 * auther: baiiu
 * time: 16/6/10 10 16:37
 * description:
 */
public class BaseFragment extends Fragment {


    @Override public void onDestroy() {
        super.onDestroy();

        UIUtil.getRefWatcher()
              .watch(this);
    }
}
