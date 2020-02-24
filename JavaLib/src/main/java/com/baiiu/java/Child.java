package com.baiiu.java;

/**
 * author: zhuzhe
 * time: 2020-02-21
 * description:
 */
public class Child extends Parent {

    public static final int A = 10;

    public static int b = 10;

    public int c = 10;

    static {
        System.out.println("Child静态代码块 ==== " + b++);
    }

    {
        System.out.println("Child构造代码块 ==== " + b++ + ", " + c++);
    }


    public Child() {
        System.out.println("Child构造函数 ==== " + b++ + ", " + c++);
    }

    @Override
    public void function() {
        System.out.println("Child方法 ==== " + b++ + ", " + c++);
    }

    public static void functionStatic() {
        System.out.println("Child static方法 ==== " + b++);
    }
}
