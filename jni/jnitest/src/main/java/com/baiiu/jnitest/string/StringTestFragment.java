package com.baiiu.jnitest.string;

import com.baiiu.jnitest.base.BaseFragment;

public class StringTestFragment extends BaseFragment {

    static {
        System.loadLibrary("string-lib");
    }

    @Override
    protected void initOnCreateView() {
        callNativeString("java Str");

        stringMethod("abcdefg");
    }

    public native String callNativeString(String str);

    public native void stringMethod(String str);

}
