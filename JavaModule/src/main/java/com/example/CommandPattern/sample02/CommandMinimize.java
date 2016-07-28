package com.example.CommandPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/25 15:11
 * description:
 */
public class CommandMinimize extends Command {
    private HandlerMinimize handler;

    public CommandMinimize() {
        handler = new HandlerMinimize();
    }

    @Override public void execute() {
        handler.action();
    }
}
