package com.example.BridgePattern.sample03;

/**
 * author: baiiu
 * date: on 16/7/21 14:26
 * description:
 */
public class DataShowLine extends DataShow {
    public DataShowLine(DataSource dataSource) {
        super(dataSource);
    }

    @Override public void show() {
        System.out.println(dataSource.toString() + "画折线图");
    }
}
