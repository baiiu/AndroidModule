package com.baiiu.jnitest.thread;

import com.baiiu.jnitest.base.BaseFragment;

public class ThreadCreateFragment extends BaseFragment {
    static {
        System.loadLibrary("thread-lib");
    }

    @Override
    protected void initOnCreateView() {
//        createNativeThread();
//        createNativeThreadWithArgs();
        joinNativeThread();
    }

    // 创建线程
    private native void createNativeThread();

    // 创建线程并传参
    private native void createNativeThreadWithArgs();

    private native void joinNativeThread();

}
