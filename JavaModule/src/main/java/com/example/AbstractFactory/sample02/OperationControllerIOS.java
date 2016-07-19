package com.example.AbstractFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/19 12:07
 * description:
 */
public class OperationControllerIOS implements OperationController {
    @Override public void control() {
        System.out.println("IOS -- 游戏界面控制");
    }
}
