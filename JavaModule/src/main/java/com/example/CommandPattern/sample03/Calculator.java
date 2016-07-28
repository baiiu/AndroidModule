package com.example.CommandPattern.sample03;

/**
 * author: baiiu
 * date: on 16/7/25 15:53
 * description:
 */
public class Calculator {

    private Command command;

    public Calculator(Command command) {
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void compute(int value) {
        System.out.println("计算结果为: " + command.execute(value));
    }

    public void undo() {
        System.out.println("撤销后结果为: " + command.undo());
    }

}
