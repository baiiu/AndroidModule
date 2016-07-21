package com.example.BuilderPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/20 14:24
 * description:
 */
public abstract class Builder {
    protected Product product = new Product();

    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();

    public abstract void buildPartD();

    public Product getProduct() {
        return product;
    }
}

