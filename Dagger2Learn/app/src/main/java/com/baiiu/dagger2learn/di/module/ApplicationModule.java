package com.baiiu.dagger2learn.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author: baiiu
 * date: on 16/6/12 16:21
 * description:
 */
@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }

}
