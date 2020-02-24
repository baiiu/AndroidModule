package com.baiiu.java;

/**
 * author: zhuzhe
 * time: 2020-02-21
 * description:
 */
public class Parent {

    public static final int A = 10;

    public static int b = 11;

    public int c = 10;

    static {
        System.out.println("Parent静态代码块 ==== " + b++);
    }

    {
        System.out.println("Parent构造代码块 ==== " + b++ + ", " + c++);
    }


    public Parent() {
        System.out.println("Parent构造函数 ==== " + b++ + ", " + c++);
    }

    public void function() {
        System.out.println("Parent方法 ==== " + b++ + ", " + c++);
    }

    public static void functionStatic() {
        System.out.println("Parent static方法 ==== " + b++);
    }
}
