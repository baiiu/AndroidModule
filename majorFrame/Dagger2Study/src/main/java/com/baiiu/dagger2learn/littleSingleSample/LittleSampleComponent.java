package com.baiiu.dagger2learn.littleSingleSample;

import com.baiiu.dagger2learn.di.scope.PerFragment;
import dagger.Component;

/**
 * author: baiiu
 * date: on 16/6/13 17:27
 * description:
 */
@PerFragment
@Component
public interface LittleSampleComponent {
    void inject(LittleSingleSampleFragment littleSingleSampleFragment);
}
