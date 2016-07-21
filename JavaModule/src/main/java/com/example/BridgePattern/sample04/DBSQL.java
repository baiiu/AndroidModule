package com.example.BridgePattern.sample04;

/**
 * author: baiiu
 * date: on 16/7/21 14:55
 * description:
 */
public class DBSQL implements DB {
    @Override public void fromDB() {
        System.out.println("从SQL");
    }

    @Override public String toString() {
        return "DBSQL{}";
    }
}
