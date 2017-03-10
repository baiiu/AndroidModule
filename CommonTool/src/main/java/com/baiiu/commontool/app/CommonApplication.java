package com.baiiu.commontool.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * author: baiiu
 * date: on 16/1/10 21:03
 */
public class CommonApplication extends Application {

    private static Context mContext;
    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        refWatcher = LeakCanary.install(this);
    }


    public static Context getContext() {
        return mContext;
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
