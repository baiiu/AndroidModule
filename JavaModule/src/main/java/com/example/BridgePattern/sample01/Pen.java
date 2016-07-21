package com.example.BridgePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/21 10:11
 * description:
 */
public class Pen {
    //毛笔的颜色
    private Color color;
    private Size size;

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void draw() {
        System.out.println(color + ", " + size);
    }

}
