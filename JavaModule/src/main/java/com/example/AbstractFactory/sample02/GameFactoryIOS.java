package com.example.AbstractFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/19 12:11
 * description:
 */
public class GameFactoryIOS implements GameFactory {
    @Override public InterfaceController createInterfaceController() {
        return new InterfaceControllerIOS();
    }

    @Override public OperationController createOperationController() {
        return new OperationControllerIOS();
    }
}
