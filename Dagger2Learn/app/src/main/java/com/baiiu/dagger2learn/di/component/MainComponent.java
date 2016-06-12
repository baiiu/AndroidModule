package com.baiiu.dagger2learn.di.component;

import com.baiiu.dagger2learn.MainActivity;
import com.baiiu.dagger2learn.di.module.ApplicationModule;

import dagger.Component;

/**
 * author: baiiu
 * date: on 16/6/12 19:31
 * description:
 */
@Component(
        dependencies = ApplicationModule.class
)
public interface MainComponent {

    void inject(MainActivity mainActivity);


}
