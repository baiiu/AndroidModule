package com.example.SimpleFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/18 16:47
 * description: 简单工程模式
 *
 * Sunny软件公司欲基于Java语言开发一套图表库，
 * 该图表库可以为应用系统提供各种不同外观的图表，例如柱状图、饼状图、折线图等。
 *
 * Sunny软件公司图表库设计人员希望为应用系统开发人员提供一套灵活易用的图表库，而且可以较为方便地对图表库进行扩展，以便能够在将来增加一些新类型的图表。
 */
public class ZZMainClass {

    public static void main(String[] args) {
        /**
         * 为了简化,将工厂类放入抽象父类中
         */
        IChart chart = IChart.getChart(ChartFactory.CHART_HISTOGRAM);
        chart.display();
    }

}
