package com.example.BridgePattern.sample03;

/**
 * author: baiiu
 * date: on 16/7/21 14:17
 * description:
 */
public abstract class DataShow {

    protected DataSource dataSource;

    public DataShow(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public abstract void show();
}
