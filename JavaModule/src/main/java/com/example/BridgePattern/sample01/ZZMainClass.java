package com.example.BridgePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/21 10:09
 * description:
 *
 * 在毛笔中，颜色和型号实现了分离，增加新的颜色或者型号对另一方都没有任何影响。
 * 如果使用软件工程中的术语，我们可以认为在蜡笔中颜色和型号之间存在较强的耦合性，而毛笔很好地将二者解耦，使用起来非常灵活，扩展也更为方便。
 */
public class ZZMainClass {

    public static void main(String[] args) {
        Pen pen = new Pen();
        pen.setSize(Size.BIG);
        pen.setColor(Color.RED);

        pen.draw();
    }
}
