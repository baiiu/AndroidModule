package com.baiiu.nativeleak;

import android.app.Application;

/**
 * author: baiiu
 * time: 2021/5/21
 * description:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        XHook.get().init();
    }

}
