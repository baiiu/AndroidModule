package com.example.CommandPattern.sample03;

/**
 * author: baiiu
 * date: on 16/7/25 15:51
 * description:
 */
public class Adder {
    private int num;

    public int add(int value) {
        num += value;
        return num;
    }
}
