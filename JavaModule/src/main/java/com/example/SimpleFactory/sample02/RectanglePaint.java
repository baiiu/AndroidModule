package com.example.SimpleFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/18 19:42
 * description:
 */
public class RectanglePaint extends Paint {
    @Override public void draw() {
        System.out.println("画方形");
    }

    @Override public void erase() {
        System.out.println("擦除圆");
    }
}
