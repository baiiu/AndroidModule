package com.example.AbstractFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/19 12:11
 * description:
 */
public class GameFactoryAnroid implements GameFactory {
    @Override public InterfaceController createInterfaceController() {
        return new InterfaceControllerAndroid();
    }

    @Override public OperationController createOperationController() {
        return new OperationControllerAndroid();
    }
}
