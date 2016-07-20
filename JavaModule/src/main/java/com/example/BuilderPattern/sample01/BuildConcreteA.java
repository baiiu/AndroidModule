package com.example.BuilderPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/20 14:35
 * description:
 */
public class BuildConcreteA extends Builder {
    @Override public void buildPartA() {
        product.setPartA("PartA");
    }

    @Override public void buildPartB() {
        product.setPartB("PartB");
    }

    @Override public void buildPartC() {
        product.setPartC("PartC");
    }

    @Override public void buildPartD() {
        product.setPartD("PartD");
    }
}
