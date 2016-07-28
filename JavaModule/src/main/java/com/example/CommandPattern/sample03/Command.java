package com.example.CommandPattern.sample03;

/**
 * author: baiiu
 * date: on 16/7/25 15:49
 * description:
 */
abstract class Command {

    public abstract int execute(int value);

    public abstract int undo();
}
