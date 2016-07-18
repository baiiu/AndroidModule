package com.example.FactoryMethod.sample01;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:22
 * description:
 */
public class FileLoggerFactory implements LoggerFactory {
    @Override public Logger provideLogger() {
        System.out.println("初始化FileLogger复杂逻辑");
        return new FileLogger();
    }
}
