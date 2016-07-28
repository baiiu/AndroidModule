package com.example.CommandPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/25 14:29
 * description: 具体命令类
 */
public class ConcreteCommand extends Command {
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override public void execute() {
        receiver.action();
    }
}
