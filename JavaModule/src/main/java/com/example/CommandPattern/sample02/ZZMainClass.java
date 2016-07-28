package com.example.CommandPattern.sample02;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description: 可以看到,使用起来很方便,而且很容易替换.
 * 分离了请求发送者和请求接受者.
 */
class ZZMainClass {

    public static void main(String[] args) {

        SettingWindow settingWindow = new SettingWindow("一个界面");

        //创建按钮
        Button buttonOne = new Button("Button One");
        buttonOne.setCommand(new CommandHelper());

        Button buttonTwo = new Button("Button Two");
        buttonTwo.setCommand(new CommandMinimize());

        //buttonOne.onClick();
        //buttonTwo.onClick();


        //放入一个Window中
        settingWindow.addButton(buttonOne);
        settingWindow.addButton(buttonTwo);


        settingWindow.display();

    }
}
