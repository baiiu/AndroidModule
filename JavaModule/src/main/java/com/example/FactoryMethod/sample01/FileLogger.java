package com.example.FactoryMethod.sample01;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:20
 * description:
 */
public class FileLogger implements Logger {
    @Override public void writeLog() {
        System.out.println("用文件存储日志");
    }
}
