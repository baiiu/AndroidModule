package com.baiiu.dagger2learn.doubleDependenciesSample.bean;

import javax.inject.Inject;

/**
 * auther: baiiu
 * time: 16/6/14 14 21:34
 * description:
 */
public class Two {

    private One one;

    @Inject
    public Two(One one) {
        this.one = one;
    }

    @Override public String toString() {
        return "Two{" +
                "one=" + one.toString() +
                '}';
    }
}
