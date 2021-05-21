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

        // hook so loader，放在Application中调用
        XHook.get().init();
    }

}
