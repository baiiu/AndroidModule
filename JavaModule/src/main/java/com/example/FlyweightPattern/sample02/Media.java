package com.example.FlyweightPattern.sample02;

import com.example.FlyweightPattern.sample01.Coordinates;

/**
 * author: baiiu
 * date: on 16/7/22 14:00
 * description:
 */
public abstract class Media {
    protected String name;

    public Media(String name) {
        this.name = name;
    }

    public abstract void show(Coordinates coordinates);

}
