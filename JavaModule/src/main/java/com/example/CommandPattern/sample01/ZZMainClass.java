package com.example.CommandPattern.sample01;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description: 命令模式基本模型
 */
public class ZZMainClass {

    public static void main(String[] args) {

        new Invoker(new ConcreteCommand(new Receiver())).call();
    }

}
