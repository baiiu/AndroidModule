package com.baiiu.dagger2learn.littleSample;

import dagger.Component;

/**
 * auther: baiiu
 * time: 16/6/12 12 22:53
 * description:
 */

/*
    Component就是一个将Module中的依赖注入到目标类中的注入器(组件)。
 */
@Component(
        modules = FruitModule.class)
public interface FruitComponent {

    /*
        添加注入方法,连接目标类.
        一般使用inject做为方法名，方法参数为对应的目标类
     */
    void inject(FruitFragment fruitFragment);

}
