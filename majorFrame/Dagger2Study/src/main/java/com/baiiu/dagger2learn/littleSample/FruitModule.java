package com.baiiu.dagger2learn.littleSample;

import com.baiiu.dagger2learn.di.qualifier.FruitType;
import dagger.Module;
import dagger.Provides;

/**
 * auther: baiiu
 * time: 16/6/12 12 22:48
 * description:
 */

/*
    Module其实就是一个依赖的制造工厂，只需要在里面提供依赖就可。
    一个完整的Module必须拥有@Module与@Provides注解
 */
@Module
public class FruitModule {

    //@Named("A")
    @FruitType(1) @Provides Fruit provideFruitA() {
        return new Fruit("redA", "bigA");
    }


    /*
        @Provides方法可以带输入参数，其参数由Module集合中的其他@Provides方法提供
     */

    //@Named("B")
    @FruitType(2) @Provides Fruit provideFruitB(String color) {
        return new Fruit(color, "bigB");
    }

    @Provides String color() {
        return "redB";
    }

}
