package com.baiiu.dagger2learn;

import android.app.Application;

import com.baiiu.dagger2learn.di.component.ApplicationComponent;
import com.baiiu.dagger2learn.di.component.DaggerApplicationComponent;
import com.baiiu.dagger2learn.di.module.ApplicationModule;

/**
 * author: baiiu
 * date: on 16/6/12 16:35
 * description:
 */
public class MyApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
