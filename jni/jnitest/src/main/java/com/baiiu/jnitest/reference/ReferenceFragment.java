package com.baiiu.jnitest.reference;


import com.baiiu.jnitest.base.BaseFragment;

/**
 * 局部引用、弱引用、全局引用；
 */
public class ReferenceFragment extends BaseFragment {
    static {
        System.loadLibrary("reference-lib");
    }

    @Override
    protected void initOnCreateView() {
        android.util.Log.e("mLogU", "localReference: " + localReference());
        android.util.Log.e("mLogU", "globalReference: " + globalReference());
        android.util.Log.e("mLogU", "weakReference: " + weakReference());
    }

    private native String localReference();

    private native String globalReference();

    private native String weakReference();
}
