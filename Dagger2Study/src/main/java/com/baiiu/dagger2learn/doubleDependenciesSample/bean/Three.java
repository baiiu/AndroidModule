package com.baiiu.dagger2learn.doubleDependenciesSample.bean;

/**
 * auther: baiiu
 * time: 16/6/14 14 21:37
 * description:
 */
public class Three {

    private Two two;

    public Three(Two two) {
        this.two = two;
    }

    @Override public String toString() {
        return "Three{" +
                "two=" + two.toString() +
                '}';
    }
}
