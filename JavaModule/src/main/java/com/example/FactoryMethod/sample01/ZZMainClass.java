package com.example.FactoryMethod.sample01;

/**
 * auther: baiiu
 * time: 16/7/19 19 06:17
 * description: 工厂方法模式
 *
 * Sunny软件公司欲开发一个系统运行日志记录器(Logger)，该记录器可以通过多种途径保存系统的运行日志，
 * 如通过文件记录或数据库记录，用户可以通过修改配置文件灵活地更换日志记录方式。
 *
 * 在设计各类日志记录器时，Sunny公司的开发人员发现:
 * 1. 需要对日志记录器进行一些初始化工作，
 * 2. 初始化参数的设置过程较为复杂，
 * 3. 而且某些参数的设置有严格的先后次序，否则可能会发生记录失败。
 *
 * 如何封装记录器的初始化过程并保证多种记录器切换的灵活性是Sunny公司开发人员面临的一个难题。
 *
 * 工厂方法相比较于简单工厂模式,将工厂细分到每个对应的实体产品类上,方便实例化每个对象的复杂逻辑操作.
 * 并且这样易扩展,降低耦合,弥补了简单工厂的不足.
 */
public class ZZMainClass {

    public static void main(String[] args) {
        //可通过文件读取方式获取工厂实例
        FileLoggerFactory loggerFactory = new FileLoggerFactory();

        //不变的代码
        Logger logger = loggerFactory.provideLogger();
        logger.writeLog();
    }

}
