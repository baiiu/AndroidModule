package com.baiiu.dagger2learn.doubleDependenciesSample;

import dagger.Component;

/**
 * auther: baiiu
 * time: 16/6/14 14 21:25
 * description:
 */
@Component(
        modules = OneModule.class

)
public interface DoubleDependenciesComponent {
    void inject(DoubleDependenciesFragment doubleDependenciesFragment);
}
