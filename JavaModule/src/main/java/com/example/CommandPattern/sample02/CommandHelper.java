package com.example.CommandPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/25 15:11
 * description: Command实现类持有Receiver,真正的请求处理类
 */
public class CommandHelper extends Command {
    private HandlerHelper handler;

    public CommandHelper() {
        handler = new HandlerHelper();
    }

    @Override public void execute() {
        handler.action();
    }
}
