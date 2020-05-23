package com.baiiu.jnitest.basic;

import android.widget.TextView;
import android.widget.Toast;

import com.baiiu.jnitest.R;
import com.baiiu.jnitest.base.BaseFragment;

public class BasicFragment extends BaseFragment {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_basic;
    }

    @Override
    protected void initOnCreateView() {
        TextView textView = mView.findViewById(R.id.textView);
        textView.setText(stringFromJNI());

        testCallJava(this);
    }

    public native String stringFromJNI();


    private int code = 10;
    private String msg = "hello world";

    public void cCallJava(String str) {
        android.util.Log.e("mLogU", "cCallJava: " + str);

        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT)
                .show();
    }


    public native void testCallJava(BasicFragment activity);
}
