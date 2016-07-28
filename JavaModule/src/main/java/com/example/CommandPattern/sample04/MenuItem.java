package com.example.CommandPattern.sample04;

/**
 * author: baiiu
 * date: on 16/7/25 16:24
 * description: 菜单项,作为Invoker
 */
public class MenuItem {

    private String name;
    private Command command;

    public MenuItem(Command command) {
        this.command = command;
    }

    public MenuItem(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void onClick() {
        System.out.println("点击了" + name);
        command.execute();
    }

}
