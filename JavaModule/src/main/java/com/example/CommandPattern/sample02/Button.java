package com.example.CommandPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/25 15:02
 * description: Invoker类,封装真正的请求,持有Command类,调用Command类的execute()方法
 */
class Button {
    private String name;
    private Command command;

    public Button(String name) {
        this.name = name;
    }

    public Button(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void onClick() {
        System.out.println("点击按钮" + name);
        command.execute();
    }

}
