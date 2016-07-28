package com.example.CommandPattern.sample04;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {

        Menu menu = new Menu();


        menu.addMenuItem(new MenuItem("Item01", new CommandCreate()));
        menu.addMenuItem(new MenuItem("Item02", new CommandOpen()));
        menu.addMenuItem(new MenuItem("Item03", new CommandEdit()));

        menu.clickTest();

    }
}
