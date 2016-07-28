package com.example.CommandPattern.sample04;

/**
 * author: baiiu
 * date: on 16/7/25 16:32
 * description:
 */
public class CommandEdit extends Command {

    @Override public void execute() {
        boardScreen.edit();
    }
}
