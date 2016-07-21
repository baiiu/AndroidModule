package com.example.DecoratorPattern.sample02;

/**
 * auther: baiiu
 * time: 16/7/21 21 23:26
 * description:
 */
public class Encryption implements IEncrypt {
    protected String s;

    public Encryption(String s) {
        this.s = s;
    }

    @Override public String encrypt() {
        return s;
    }

}
