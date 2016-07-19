package com.example.SinglePattern;

/**
 * author: baiiu
 * date: on 16/7/19 15:33
 * description: 饿汗式,加载类时就初始化
 */
public class EagerSingleton {
    private static final EagerSingleton EAGER_SINGLETON = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton instance() {
        return EAGER_SINGLETON;
    }

}
