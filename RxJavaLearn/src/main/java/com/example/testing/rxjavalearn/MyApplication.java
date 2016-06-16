package com.example.testing.rxjavalearn;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

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


        Logger
                // default PRETTYLOGGER or use just init()
                .init("mLogUtil")
                // default 2
                .methodCount(3)
                // default LogLevel.FULL
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                // default 0
                .methodOffset(2);
    }
}
