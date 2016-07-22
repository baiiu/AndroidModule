package com.example.FlyweightPattern.sample02;

import com.example.FlyweightPattern.sample01.Coordinates;

/**
 * author: baiiu
 * date: on 16/7/22 14:22
 * description:
 */
public class MediaVideo extends Media {

    public MediaVideo(String name) {
        super(name);
    }

    @Override public void show(Coordinates coordinates) {
        System.out.println(name + "Video show " + coordinates.toString());
    }
}
