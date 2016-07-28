package com.example.ProxyPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/22 17:16
 * description:
 */
public class ProxySubject implements Subject {
    private Subject subject;

    public ProxySubject() {
        subject = new RealSubject();
    }

    @Override public void request() {
        System.out.println("before====");
        subject.request();
        System.out.println("after====");
    }
}
