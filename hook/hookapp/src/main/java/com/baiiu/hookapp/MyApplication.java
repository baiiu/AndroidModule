package com.baiiu.hookapp;

import android.app.Application;
import android.content.Context;

/**
 * author: zhuzhe
 * time: 2020-01-15
 * description:
 */
public class MyApplication extends Application {
    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }
}
