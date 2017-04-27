package com.baiiu.toucheventstudy.base;

import android.app.Application;
import android.content.Context;

/**
 * author: baiiu
 * date: on 17/4/24 18:20
 * description:
 */
public class MyApplication extends Application {
    public static Context sContext;

    @Override public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
