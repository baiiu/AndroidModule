package com.example.AbstractFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/19 12:00
 * description:
 *
 * Sunny软件公司欲推出一款新的手机游戏软件，该软件能够支持Symbian、Android和Windows Mobile等多个手机操作系统平台，
 * 针对不同的手机操作系统，该游戏软件提供了不同的游戏操作控制(OperationController)类和游戏界面控制(InterfaceController)类，并提供相应的工厂类来封装这些类的初始化过程。
 *
 * 软件要求具有较好的扩展性以支持新的操作系统平台，为了满足上述需求，试采用抽象工厂模式对其进行设计。
 */
public class ZZMainClass {

    public static void main(String[] args) {

        GameFactory gameFactory = new GameFactoryAnroid();


        InterfaceController interfaceController = gameFactory.createInterfaceController();
        OperationController operationController = gameFactory.createOperationController();

        interfaceController.control();
        operationController.control();

    }

}
