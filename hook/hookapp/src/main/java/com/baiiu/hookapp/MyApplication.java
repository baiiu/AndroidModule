package com.baiiu.hookapp;

import android.app.Application;
import android.content.Context;

/**
 * author: zhuzhe
 * time: 2020-01-15
 * description:
 */
public class MyApplication extends Application {

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override public void onCreate() {
        super.onCreate();

    }
}
