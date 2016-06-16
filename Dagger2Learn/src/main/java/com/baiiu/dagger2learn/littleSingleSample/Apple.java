package com.baiiu.dagger2learn.littleSingleSample;

import javax.inject.Inject;

/**
 * author: baiiu
 * date: on 16/6/13 17:25
 * description:
 */
public class Apple {
    public String name;

    @Inject public Apple() {
        this.name = "initApple";
    }

    public Apple(String name) {
        this.name = name;
    }

    @Override public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                '}';
    }
}
