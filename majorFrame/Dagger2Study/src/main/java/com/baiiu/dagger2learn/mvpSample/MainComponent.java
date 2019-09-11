package com.baiiu.dagger2learn.mvpSample;

import com.baiiu.dagger2learn.MainActivity;
import com.baiiu.dagger2learn.di.module.ActivityModule;
import com.baiiu.dagger2learn.di.module.ApplicationModule;
import com.baiiu.dagger2learn.di.scope.PerActivity;
import dagger.Component;

/**
 * author: baiiu
 * date: on 16/6/12 19:31
 * description:
 */
@PerActivity
@Component(
        dependencies = ApplicationModule.class,
        modules = { MainModule.class, ActivityModule.class }

)
public interface MainComponent {

    void inject(MainActivity mainActivity);

    MainFragmentComponent mainFragmentComponent();

}
