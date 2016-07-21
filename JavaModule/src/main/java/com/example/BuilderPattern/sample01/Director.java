package com.example.BuilderPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/20 14:33
 * description:
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }


    public Product build() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        builder.buildPartD();
        return builder.getProduct();
    }
}
