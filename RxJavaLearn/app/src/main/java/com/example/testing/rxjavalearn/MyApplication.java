package com.example.testing.rxjavalearn;

import android.app.Application;
import android.content.Context;

/**
 * auther: baiiu
 * time: 16/5/31 31 23:07
 * description:
 */
public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
