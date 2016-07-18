package com.example.FactoryMethod.sample01;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:22
 * description:
 */
public class DBLoggerFactory implements LoggerFactory {
    @Override public Logger provideLogger() {
        return new DBLogger();
    }
}
