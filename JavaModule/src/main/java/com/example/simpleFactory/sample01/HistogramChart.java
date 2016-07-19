package com.example.SimpleFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/18 17:40
 * description:
 */
public class HistogramChart extends IChart {
    public HistogramChart() {
        System.out.println("创建直方图");
    }

    @Override public void display() {
        System.out.println("展示直方图");
    }
}
