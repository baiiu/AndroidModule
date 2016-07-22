package com.example.FlyweightPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/22 13:41
 * description: 围棋棋子,抽象享元类,声明具体享元类公共的方法.
 *
 * 这些方法可以向外界提供享元对象的内部数据（内部状态），
 * 同时也可以通过这些方法来设置外部数据（外部状态）。
 */
public abstract class IgoChessman {

    //内部状态
    public abstract String getColor();


    //设置外部状态
    public void display(Coordinates coordinates) {
        System.out.println("棋子颜色: " + getColor() + "x=" + coordinates.getX() + ", y=" + coordinates.getY());
    }

}
