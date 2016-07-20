package com.example.BuilderPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/20 15:00
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {
        System.out.println(

                new Builder().memory("32G")
                        .gpu("2G")
                        .cpu("8核")
                        .os("windows")
                        .build()
                        .toString()

        );
    }
}
