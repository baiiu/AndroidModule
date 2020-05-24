package com.baiiu.jnitest.reference;

import com.baiiu.jnitest.base.BaseFragment;

import java.util.Arrays;

/**
 * 引用传递: 字符串，数组 和 对象
 * <p>
 * native访问对象字段、对象方法
 * <p>
 * native创建对象
 */
public class ReferenceFragment extends BaseFragment {

    static {
        System.loadLibrary("reference-lib");
    }

    private static int sCount = 1;

    // native修改sCount值
    public static native void nativeSetNum();

    @Override
    protected void initOnCreateView() {
        String s = callNativeStringArray(new String[]{"1", "2", "3", "4", "5"});
        android.util.Log.e("mLogU", "first is " + s);

        android.util.Log.e("mLogU", Arrays.toString(getIntArray(10)));

        Animal animal = new Animal("animal");
        android.util.Log.e("mLogU", "before: " + animal.toString());
        accessInstanceField(animal);
        accessStaticField(animal);
        android.util.Log.e("mLogU", "after: " + animal.toString());

        android.util.Log.e("mLogU", "before: " + sCount);
        nativeSetNum();
        android.util.Log.e("mLogU", "after: " + sCount);

        nativeCallMethod(animal);

        android.util.Log.e("mLogU", "createAnimal: " + createAnimal().toString());
        android.util.Log.e("mLogU", "createAnimal2: " + createAnimal2().toString());
    }

    public native String callNativeStringArray(String[] strArray);

    public native int[] getIntArray(int num);

    // native修改animal对象中静态值
    public native void accessStaticField(Animal animal);

    // native修改animal对象中变量
    public native void accessInstanceField(Animal animal);

    // native调用animal相关方法
    public native void nativeCallMethod(Animal animal);

    public native Animal createAnimal();

    public native Animal createAnimal2();

}
