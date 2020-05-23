package com.baiiu.jnitest.typeConvert;

import com.baiiu.jnitest.base.BaseFragment;

public class TypeConvertFragment extends BaseFragment {
    static {
        System.loadLibrary("typeConvert-lib");
    }

    @Override
    protected void initOnCreateView() {
        android.util.Log.e("mLogU", "call result: " + callNativeBoolean(true));
        android.util.Log.e("mLogU", "call result: " + callNativeByte((byte) 126));
        android.util.Log.e("mLogU", "call result: " + callNativeChar('a'));
        android.util.Log.e("mLogU", "call result: " + callNativeShort((short) 200));
        android.util.Log.e("mLogU", "call result: " + callNativeInt(2));
        android.util.Log.e("mLogU", "call result: " + callNativeLong(2));
        android.util.Log.e("mLogU", "call result: " + callNativeFloat(2.0F));
        android.util.Log.e("mLogU", "call result: " + callNativeDouble(2.0D));
    }

    public native boolean callNativeBoolean(boolean bool);

    public native byte callNativeByte(byte num);

    public native char callNativeChar(char num);

    public native short callNativeShort(short num);

    public native int callNativeInt(int num);

    public native long callNativeLong(long num);

    public native float callNativeFloat(float num);

    public native double callNativeDouble(double num);

}
