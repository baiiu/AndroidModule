package com.baiiu.dagger2learn.mvpSample;

import com.baiiu.dagger2learn.bean.OneApple;
import com.baiiu.dagger2learn.di.scope.PerFragment;
import dagger.Module;
import dagger.Provides;

/**
 * author: baiiu
 * date: on 16/6/13 17:42
 * description:
 */
@Module
public class MainFragmentModule {

    @Provides @PerFragment OneApple provideApple() {
        return new OneApple("red");
    }

}
