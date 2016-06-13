package com.baiiu.dagger2learn.mvpSample;

import com.baiiu.dagger2learn.di.scope.PerFragment;
import dagger.Subcomponent;

/**
 * author: baiiu
 * date: on 16/6/13 11:43
 * description:
 */
@PerFragment
@Subcomponent(
        modules = { MainFragmentModule.class }

)
public interface MainFragmentComponent {
    void inject(MainFragment mainFragment);
}
