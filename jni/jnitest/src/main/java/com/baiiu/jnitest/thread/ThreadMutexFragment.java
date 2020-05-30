package com.baiiu.jnitest.thread;

import com.baiiu.jnitest.base.BaseFragment;

public class ThreadMutexFragment extends BaseFragment {
    static {
        System.loadLibrary("thread-lib");
    }

    @Override
    protected void initOnCreateView() {
        print12to100();

        multi();
    }

    private native void print12to100();

    private native void multi();
}
