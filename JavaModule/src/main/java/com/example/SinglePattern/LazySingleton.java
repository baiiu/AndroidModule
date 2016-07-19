package com.example.SinglePattern;

/**
 * author: baiiu
 * date: on 16/7/19 15:35
 * description: 懒汉式,延迟加载,用到时再加载
 */
public class LazySingleton {
    /*
        使用volatile关键字修饰,保证同一对象在多线程环境下的修改保持同步,会增加性能损耗
     */
    private static volatile LazySingleton mLazySingleton;

    private LazySingleton() {
    }

    public static LazySingleton instance() {
        if (mLazySingleton == null) {
            synchronized (LazySingleton.class) {
                if (mLazySingleton == null) {
                    mLazySingleton = new LazySingleton();
                }
            }
        }

        return mLazySingleton;
    }

}
