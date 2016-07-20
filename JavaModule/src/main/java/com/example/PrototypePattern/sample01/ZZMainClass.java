package com.example.PrototypePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/20 10:15
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {
        ConcretePrototypeA prototype = new ConcretePrototypeA();
        prototype.setAttr("哈哈哈");

        ConcretePrototypeA one = prototype.clone();
        ConcretePrototypeA two = prototype.clone();

        System.out.println((prototype == one) + ", " + (one == two) + ", " + (prototype == two));
        System.out.println(one.getAttr() + ", " + two.getAttr());
    }
}