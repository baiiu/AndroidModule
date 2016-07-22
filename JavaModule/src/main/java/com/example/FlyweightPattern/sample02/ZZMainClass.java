package com.example.FlyweightPattern.sample02;

import com.example.FlyweightPattern.sample01.Coordinates;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description:
 *
 * Sunny软件公司欲开发一个多功能文档编辑器，在文本文档中可以插入图片、动画、视频等多媒体资料
 * 为了节约系统资源，相同的图片、动画和视频在同一个文档中只需保存一份
 * 但是可以多次重复出现，而且它们每次出现时位置和大小均可不同。
 */
public class ZZMainClass {

    public static void main(String[] args) {

        Media one = MediaFactory.INSTANCE.getMedia(MediaType.Animation, "One");
        Media one1 = MediaFactory.INSTANCE.getMedia(MediaType.Animation, "One");

        Media imageOne = MediaFactory.INSTANCE.getMedia(MediaType.Image, "one");
        Media imageTwo = MediaFactory.INSTANCE.getMedia(MediaType.Image, "two");

        System.out.println(one == one1);
        one.show(new Coordinates(1, 1, 100, 100));
        one1.show(new Coordinates(2, 2, 100, 100));


        System.out.println(imageOne == imageTwo);
        imageOne.show(new Coordinates(2, 2, 200, 300));
        imageTwo.show(new Coordinates(3, 3, 3, 3));

    }
}
