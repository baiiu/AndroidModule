package com.baiiu.dagger2learn.bean;

/**
 * author: baiiu
 * date: on 16/6/13 17:40
 * description:
 */
public class OneApple {
    public String color;

    public OneApple(String color) {
        this.color = color;
    }

    @Override public String toString() {
        return "OneApple{" +
                "color='" + color + '\'' +
                '}';
    }
}
