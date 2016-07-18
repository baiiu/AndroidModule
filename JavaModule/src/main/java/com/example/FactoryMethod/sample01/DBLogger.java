package com.example.FactoryMethod.sample01;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:20
 * description:
 */
public class DBLogger implements Logger {
    @Override public void writeLog() {
        System.out.println("初始化DBLogger复杂逻辑");
        System.out.println("用数据库存储日志");
    }
}
