package com.baiiu.jnitest.reference;

import java.util.Arrays;

/**
 * author: baiiu
 * time: 2020/5/24
 * description:
 */
public class Animal {

    protected String name;
    public static int num = 10;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                "num='" + num + '\'' +
                super.toString() +
                '}';
    }

    public int accumulate(int num) {
        android.util.Log.d("mLogU", "accumulate: " + num);

        return ++num;
    }

    public String doubleString(String s1, String s2) {
        String result = s1 + ", " + s2;
        android.util.Log.e("mLogU", "doubleString: " + result);
        return result;
    }

    public static String nativeCall(String[] strings, int num) {
        String result = Arrays.toString(strings) + ", " + num;
        android.util.Log.e("mLogU", "nativeCall: " + result);
        return result;
    }

}