package com.baiiu.dagger2learn.di.module;

import com.baiiu.dagger2learn.bean.OnePerson;
import com.baiiu.dagger2learn.di.scope.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * auther: baiiu
 * time: 16/6/12 12 21:46
 * description:
 */
@Module
public class MainModule {

    /*
        通过 @Provides 提供依赖,优先Module中的依赖
     */
    @Provides @PerActivity public OnePerson privideOnePerson() {
        return new OnePerson("LiMing", "25");
    }
}
