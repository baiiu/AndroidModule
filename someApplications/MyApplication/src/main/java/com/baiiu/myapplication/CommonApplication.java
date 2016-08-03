package com.baiiu.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * author: baiiu
 * date: on 16/1/10 21:03
 */
public class CommonApplication extends Application {

    private static Context mContext;

    @Override public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }


    public static Context getContext() {
        return mContext;
    }

}
