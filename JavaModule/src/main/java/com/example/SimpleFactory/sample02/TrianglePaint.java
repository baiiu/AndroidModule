package com.example.SimpleFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/18 19:43
 * description:
 */
public class TrianglePaint extends Paint {
    @Override public void draw() {
        System.out.println("画三角形");
    }

    @Override public void erase() {
        System.out.println("擦除三角形");
    }
}
