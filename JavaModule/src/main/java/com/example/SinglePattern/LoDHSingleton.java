package com.example.SinglePattern;

/**
 * author: baiiu
 * date: on 16/7/19 15:40
 * description: 饿汉式浪费内存,懒汉式使用volatile造成性能损耗,
 * 使用HolderClass包裹,既延迟加载,又不浪费性能
 */
public class LoDHSingleton {

    private LoDHSingleton() {
    }

    private static class HolderClass {
        private static final LoDHSingleton LO_DH_SINGLETON = new LoDHSingleton();
    }

    public static LoDHSingleton instance() {
        return HolderClass.LO_DH_SINGLETON;
    }

}
