package com.example.CommandPattern.sample04;

/**
 * author: baiiu
 * date: on 16/7/25 16:32
 * description: 具体命令类
 */
public class CommandCreate extends Command {

    @Override public void execute() {
        boardScreen.create();
    }
}
