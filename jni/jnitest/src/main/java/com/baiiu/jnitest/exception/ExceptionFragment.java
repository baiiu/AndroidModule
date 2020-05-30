package com.baiiu.jnitest.exception;

import com.baiiu.jnitest.base.BaseFragment;

public class ExceptionFragment extends BaseFragment {
    static {
        System.loadLibrary("exception-lib");
    }

    @Override
    protected void initOnCreateView() {
        nativeInvokeJavaException();

        try {
            nativeThrowException();
        } catch (Exception e) {
            android.util.Log.e("mLogU", e.toString());
        }
    }

    private int operation() {
        return 2 / 0;
    }

    /*
        native调用 operation方法，内部catch住，程序不崩溃
     */
    private native void nativeInvokeJavaException();


    private native void nativeThrowException() throws IllegalArgumentException;

}
