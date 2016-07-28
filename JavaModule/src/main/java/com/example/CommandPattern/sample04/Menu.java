package com.example.CommandPattern.sample04;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/7/25 16:24
 * description:
 */
public class Menu {

    private List<MenuItem> menuItemList = new ArrayList<>();

    public void addMenuItem(MenuItem menuItem) {
        menuItemList.add(menuItem);
    }

    public void removeMenuItem(MenuItem menuItem) {
        menuItemList.add(menuItem);
    }

    public void clickTest() {
        for (MenuItem menuItem : menuItemList) {
            menuItem.onClick();
        }
    }

}
