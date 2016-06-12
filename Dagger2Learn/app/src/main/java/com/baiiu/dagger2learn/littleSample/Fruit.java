package com.baiiu.dagger2learn.littleSample;

/**
 * auther: baiiu
 * time: 16/6/12 12 22:49
 * description:
 */
public class Fruit {
    public String color;
    public String size;


    public Fruit() {
    }

    public Fruit(String color, String size) {
        this.color = color;
        this.size = size;
    }

    @Override public String toString() {
        return "Fruit{" +
                "color='" + color + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
