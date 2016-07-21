package com.example.AdapterPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/20 17:40
 * description:
 */
public class Adapter implements Target {

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override public void request() {
        adaptee.specificRequest();
    }
}
