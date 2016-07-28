package com.example.CommandPattern.sample02;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/7/25 14:53
 * description: 界面类,一个界面有很多按钮.
 * 注意和命令队列的区别
 */
class SettingWindow {
    private String title;
    private List<Button> buttons = new ArrayList<>();


    public SettingWindow(String title) {
        this.title = title;
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void removeButton(Button button) {
        buttons.remove(button);
    }


    public void display() {
        for (Button button : buttons) {
            button.onClick();
        }
    }

}
