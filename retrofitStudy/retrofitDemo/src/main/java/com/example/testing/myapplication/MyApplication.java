package com.example.testing.myapplication;

import android.app.Application;
import android.content.Context;
//import com.facebook.stetho.Stetho;

/**
 * author: baiiu
 * date: on 16/5/17 15:39
 * description:
 */
public class MyApplication extends Application {

    public static Context mContext;

    @Override public void onCreate() {
        super.onCreate();

        mContext = this;

        //Stetho.initializeWithDefaults(this);
    }
}
