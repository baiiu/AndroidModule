package com.baiiu.dagger2learn.di.component;

import android.content.Context;

import com.baiiu.dagger2learn.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * author: baiiu
 * date: on 16/6/12 16:04
 * description:
 */
@Singleton
@Component(
        modules = {ApplicationModule.class}
)
public interface ApplicationComponent {
    Context getContext();

}
