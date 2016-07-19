package com.example.AbstractFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/19 12:10
 * description:
 */
public interface GameFactory {
    InterfaceController createInterfaceController();

    OperationController createOperationController();
}
