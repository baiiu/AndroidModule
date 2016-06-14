package com.baiiu.dagger2learn.doubleDependenciesSample;

import com.baiiu.dagger2learn.doubleDependenciesSample.bean.Three;
import com.baiiu.dagger2learn.doubleDependenciesSample.bean.Two;
import dagger.Module;
import dagger.Provides;

/**
 * auther: baiiu
 * time: 16/6/14 14 21:26
 * description:
 */
@Module
public class OneModule {

    /*
        通过@Inject One的构造函数创建依赖,此处是提供的依赖消费别的依赖
     */
    //@Provides Two provideOne(One one) {
    //    return new Two(one);
    //}

    @Provides Three provideThree(Two two) {
        return new Three(two);
    }

}
