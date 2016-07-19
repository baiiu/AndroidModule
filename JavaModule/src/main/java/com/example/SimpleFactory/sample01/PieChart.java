package com.example.SimpleFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/18 17:41
 * description:
 */
public class PieChart extends IChart {
    public PieChart() {
        System.out.println("创建饼状图");
    }

    @Override public void display() {
        System.out.println("展示饼状图");
    }
}

