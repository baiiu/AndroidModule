package com.example.SimpleFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/18 19:38
 * description:
 *
 * 使用简单工厂模式设计一个可以创建不同几何形状（如圆形、方形和三角形等）的绘图工具
 * 每个几何图形都具有绘制draw()和擦除erase()两个方法，
 * 要求在绘制不支持的几何图形时，提示一个UnSupportedShapeException。
 */
public class ZZMainClass {

    public static void main(String[] args) {
        Paint paint = Paint.getPaint(Paint.PAINT_CIRCLE);
        paint.draw();
        paint.erase();
    }

}
