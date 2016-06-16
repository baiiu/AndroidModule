package com.baiiu.dagger2learn;

import android.app.Application;
import android.content.Context;
import com.baiiu.dagger2learn.di.component.AppComponent;
import com.baiiu.dagger2learn.di.component.DaggerAppComponent;
import com.baiiu.dagger2learn.di.module.ApplicationModule;
import com.baiiu.dagger2learn.util.LogUtil;
import javax.inject.Inject;

/**
 * author: baiiu
 * date: on 16/6/12 16:35
 * description:
 */
public class MyApplication extends Application {

    /*
        需要是静态吗?
     */
    private static AppComponent mAppComponent;

    @Inject Context sContext;

    @Override public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        mAppComponent.inject(this);

        LogUtil.d(sContext.toString());
    }

    public static AppComponent getApplicationComponent() {
        return mAppComponent;
    }
}
