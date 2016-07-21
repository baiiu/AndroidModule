package com.example.DecoratorPattern.sample01;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:34
 * description:
 */
public class DecoratorScrollBar extends DecoratorComponent {
    public DecoratorScrollBar(Component component) {
        super(component);
    }

    @Override public void display() {
        System.out.println("添加滚动条");
        super.display();
    }
}
