package com.baiiu.dagger2learn.doubleDependenciesSample.bean;

import javax.inject.Inject;

/**
 * auther: baiiu
 * time: 16/6/14 14 21:27
 * description:
 */
public class One {

    @Inject public One() {
    }

    @Override public String toString() {
        return "One{}";
    }
}
