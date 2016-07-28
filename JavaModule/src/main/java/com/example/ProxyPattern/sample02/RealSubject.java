package com.example.ProxyPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/22 17:04
 * description:
 */
public class RealSubject implements Subject {
    @Override public void request() {
        System.out.println("真正的request");
    }
}
