package com.example.SimpleFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/18 17:42
 * description:
 */
public class LineChart extends IChart {
    public LineChart() {
        System.out.println("创建折线图");
    }

    @Override public void display() {
        System.out.println("展示折线图");
    }
}
