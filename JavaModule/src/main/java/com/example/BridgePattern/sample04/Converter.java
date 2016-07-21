package com.example.BridgePattern.sample04;

/**
 * author: baiiu
 * date: on 16/7/21 14:48
 * description:
 */
public abstract class Converter {
    protected DB db;

    public Converter(DB db) {
        this.db = db;
    }

    public void setDB(DB db) {
        this.db = db;
    }

    public abstract void convert();
}
