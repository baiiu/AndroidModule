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

        callJava();
        callJavaFree();
    }


    public native String callNativeString(String str);

    public native void stringMethod(String str);

    public native void callJava();

    public native void callJavaFree();

    public static String decorateUrl(String url) {
        return "decorate: " + url;
    }

    public String decorateUrlFree(String url) {
//        return "decorate free: " + url;
        return null; // GetStringUTFChars参数不能为null
    }

}
