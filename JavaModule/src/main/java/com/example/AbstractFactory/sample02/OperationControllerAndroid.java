package com.example.AbstractFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/19 12:07
 * description:
 */
public class OperationControllerAndroid implements OperationController {
    @Override public void control() {
        System.out.println("Android -- 游戏界面控制");
    }
}
