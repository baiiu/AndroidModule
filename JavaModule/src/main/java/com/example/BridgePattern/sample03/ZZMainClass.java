package com.example.BridgePattern.sample03;

/**
 * author: baiiu
 * date: on 16/7/21 14:04
 * description:
 *
 * 在某系统的报表处理模块中，需要将报表显示和数据采集分开，
 * 系统可以有多种报表显示方式,如折线图,饼状图等.
 * 也可以有多种数据采集方式，如可以从文本文件中读取数据，也可以从数据库中读取数据，还可以从Excel文件中获取数据。
 *
 * 如果需要从Excel文件中获取数据，则需要调用与Excel相关的API，而这个API是现有系统所不具备的，该API由厂商提供。使用适配器模式和桥接模式设计该模块。
 */
public class ZZMainClass {

    public static void main(String[] args) {
        DataShow dataShow = new DataShowLine(new DataSourceFile());
        dataShow.show();


        dataShow.setDataSource(new ExcelAdapter(new ExcelAPI()));
        dataShow.show();
    }
}
