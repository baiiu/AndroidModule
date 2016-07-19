package com.example.SimpleFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/18 19:41
 * description:
 */
public class CirclePaint extends Paint {
    @Override public void draw() {
        System.out.println("画圆");
    }

    @Override public void erase() {
        System.out.println("擦除圆");
    }
}
