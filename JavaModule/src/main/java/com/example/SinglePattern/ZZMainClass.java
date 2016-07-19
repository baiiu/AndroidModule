package com.example.SinglePattern;

/**
 * author: baiiu
 * date: on 16/7/19 15:35
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {
        System.out.println("饿汉式: " + (EagerSingleton.instance() == EagerSingleton.instance()));
        System.out.println("懒汉式: " + (LazySingleton.instance() == LazySingleton.instance()));
        System.out.println("LoDH式: " + (LoDHSingleton.instance() == LoDHSingleton.instance()));
        System.out.println("枚举: " + (EnumSingleton.INSTANCE == EnumSingleton.INSTANCE));
    }

}
