package com.example.CommandPattern.sample04;

/**
 * author: baiiu
 * date: on 16/7/25 16:26
 * description: 抽象命令类
 */
abstract class Command {
    protected BoardScreen boardScreen;

    public Command() {
        boardScreen = new BoardScreen();
    }

    public abstract void execute();
}
