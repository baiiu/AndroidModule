package com.example.BuilderPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/20 14:36
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {
        Director director = new Director(new BuildConcreteA());
        Product product = director.build();

        System.out.println(product.toString());
    }
}
