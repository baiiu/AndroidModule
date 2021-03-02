package com.baiiu.jnitest.epoll;

import com.baiiu.jnitest.base.BaseFragment;

/**
 * author: baiiu
 * time: 2021/3/2
 * description:
 */
public class EpollFragment extends BaseFragment {

    static {
        System.loadLibrary("epoll-lib");
    }

    @Override
    protected void initOnCreateView() {
        testEpoll();
    }

    private native void testEpoll();

}
