package com.example.CommandPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/25 14:27
 * description: 调用者
 */
public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void call() {
        command.execute();
    }

}
